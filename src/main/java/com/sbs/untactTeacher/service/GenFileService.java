package com.sbs.untactTeacher.service;


import com.google.common.base.Joiner;
import com.sbs.untactTeacher.dao.GenFileDao;
import com.sbs.untactTeacher.dto.GenFile;
import com.sbs.untactTeacher.dto.ResultData;
import com.sbs.untactTeacher.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class GenFileService {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    @Autowired
    private GenFileDao genFileDao;

    public ResultData saveMeta(String relTypeCode, int relId, String typeCode, String type2Code, int fileNo,
                               String originFileName, String fileExtTypeCode, String fileExtType2Code, String fileExt, int fileSize,
                               String fileDir) {

        Map<String, Object> param = Util.mapOf("relTypeCode", relTypeCode, "relId", relId, "typeCode", typeCode,
                "type2Code", type2Code, "fileNo", fileNo, "originFileName", originFileName, "fileExtTypeCode",
                fileExtTypeCode, "fileExtType2Code", fileExtType2Code, "fileExt", fileExt, "fileSize", fileSize,
                "fileDir", fileDir);
        genFileDao.saveMeta(param);

        int id = Util.getAsInt(param.get("id"), 0);
        return new ResultData("S-1", "성공하였습니다.", "id", id);
    }

    public ResultData save(MultipartFile multipartFile, String relTypeCode, int relId, String typeCode, String type2Code, int fileNo) {
        String fileInputName = multipartFile.getName();
        String[] fileInputNameBits = fileInputName.split("__");

        if (fileInputNameBits[0].equals("file") == false) {
            return new ResultData("F-1", "파라미터 명이 올바르지 않습니다.");
        }

        int fileSize = (int) multipartFile.getSize();

        if (fileSize <= 0) {
            return new ResultData("F-2", "파일이 업로드 되지 않았습니다.");
        }

        String originFileName = multipartFile.getOriginalFilename();
        String fileExtTypeCode = Util.getFileExtTypeCodeFromFileName(multipartFile.getOriginalFilename());
        String fileExtType2Code = Util.getFileExtType2CodeFromFileName(multipartFile.getOriginalFilename());
        String fileExt = Util.getFileExtFromFileName(multipartFile.getOriginalFilename()).toLowerCase();

        if (fileExt.equals("jpeg")) {
            fileExt = "jpg";
        } else if (fileExt.equals("htm")) {
            fileExt = "html";
        }

        String fileDir = Util.getNowYearMonthDateStr();

        if (relId > 0) {
            GenFile oldGenFile = getGenFile(relTypeCode, relId, typeCode, type2Code, fileNo);

            if (oldGenFile != null) {
                deleteGenFile(oldGenFile);
            }
        }

        ResultData saveMetaRd = saveMeta(relTypeCode, relId, typeCode, type2Code, fileNo, originFileName,
                fileExtTypeCode, fileExtType2Code, fileExt, fileSize, fileDir);
        int newGenFileId = (int) saveMetaRd.getBody().get("id");

        // 새 파일이 저장될 폴더(io파일) 객체 생성
        String targetDirPath = genFileDirPath + "/" + relTypeCode + "/" + fileDir;
        java.io.File targetDir = new java.io.File(targetDirPath);

        // 새 파일이 저장될 폴더가 존재하지 않는다면 생성
        if (targetDir.exists() == false) {
            targetDir.mkdirs();
        }

        String targetFileName = newGenFileId + "." + fileExt;
        String targetFilePath = targetDirPath + "/" + targetFileName;

        // 파일 생성(업로드된 파일을 지정된 경로롤 옮김)
        try {
            multipartFile.transferTo(new File(targetFilePath));
        } catch (IllegalStateException | IOException e) {
            return new ResultData("F-3", "파일저장에 실패하였습니다.");
        }

        return new ResultData("S-1", "파일이 생성되었습니다.", "id", newGenFileId, "fileRealPath", targetFilePath, "fileName",
                targetFileName, "fileInputName", fileInputName);
    }

    public ResultData save(MultipartFile multipartFile) {
        String fileInputName = multipartFile.getName();
        String[] fileInputNameBits = fileInputName.split("__");

        String relTypeCode = fileInputNameBits[1];
        int relId = Integer.parseInt(fileInputNameBits[2]);
        String typeCode = fileInputNameBits[3];
        String type2Code = fileInputNameBits[4];
        int fileNo = Integer.parseInt(fileInputNameBits[5]);

        return save(multipartFile, relTypeCode, relId, typeCode, type2Code, fileNo);
    }

    public ResultData save(MultipartFile multipartFile, int relId) {
        String fileInputName = multipartFile.getName();
        String[] fileInputNameBits = fileInputName.split("__");

        String relTypeCode = fileInputNameBits[1];
        String typeCode = fileInputNameBits[3];
        String type2Code = fileInputNameBits[4];
        int fileNo = Integer.parseInt(fileInputNameBits[5]);

        return save(multipartFile, relTypeCode, relId, typeCode, type2Code, fileNo);
    }

    public List<GenFile> getGenFiles(String relTypeCode, int relId, String typeCode, String type2Code) {
        return genFileDao.getGenFiles(relTypeCode, relId, typeCode, type2Code);
    }

    public GenFile getGenFile(String relTypeCode, int relId, String typeCode, String type2Code, int fileNo) {
        return genFileDao.getGenFile(relTypeCode, relId, typeCode, type2Code, fileNo);
    }

    public ResultData saveFiles(Map<String, Object> param, MultipartRequest multipartRequest) {
        // 업로드 시작
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        Map<String, ResultData> filesResultData = new HashMap<>();
        List<Integer> genFileIds = new ArrayList<>();

        for (String fileInputName : fileMap.keySet()) {
            MultipartFile multipartFile = fileMap.get(fileInputName);

            if (multipartFile.isEmpty() == false) {
                ResultData fileResultData = save(multipartFile);
                int genFileId = (int) fileResultData.getBody().get("id");
                genFileIds.add(genFileId);

                filesResultData.put(fileInputName, fileResultData);
            }
        }

        String genFileIdsStr = Joiner.on(",").join(genFileIds);

        // 삭제 시작
        int deleteCount = 0;

        for (String inputName : param.keySet()) {
            String[] inputNameBits = inputName.split("__");

            if (inputNameBits[0].equals("deleteFile")) {
                String relTypeCode = inputNameBits[1];
                int relId = Integer.parseInt(inputNameBits[2]);
                String typeCode = inputNameBits[3];
                String type2Code = inputNameBits[4];
                int fileNo = Integer.parseInt(inputNameBits[5]);

                GenFile oldGenFile = getGenFile(relTypeCode, relId, typeCode, type2Code, fileNo);

                if (oldGenFile != null) {
                    deleteGenFile(oldGenFile);
                    deleteCount++;
                }
            }
        }

        return new ResultData("S-1", "파일을 업로드하였습니다.", "filesResultData", filesResultData, "genFileIdsStr",
                genFileIdsStr, "deleteCount", deleteCount);
    }

    public void changeRelId(int id, int relId) {
        genFileDao.changeRelId(id, relId);
    }

    public void deleteGenFiles(String relTypeCode, int relId) {
        List<GenFile> genFiles = genFileDao.getGenFilesByRelTypeCodeAndRelId(relTypeCode, relId);

        for (GenFile genFile : genFiles) {
            deleteGenFile(genFile);
        }
    }

    private void deleteGenFile(GenFile genFile) {
        String filePath = genFile.getFilePath(genFileDirPath);
        Util.deleteFile(filePath);

        genFileDao.deleteFile(genFile.getId());
    }

    public GenFile getGenFile(int id) {
        return genFileDao.getGenFileById(id);
    }

    public Map<Integer, Map<String, GenFile>> getFilesMapKeyRelIdAndFileNo(String relTypeCode, List<Integer> relIds,
                                                                           String typeCode, String type2Code) {
        List<GenFile> genFiles = genFileDao.getGenFilesRelTypeCodeAndRelIdsAndTypeCodeAndType2Code(relTypeCode, relIds,
                typeCode, type2Code);
        Map<String, GenFile> map = new HashMap<>();
        Map<Integer, Map<String, GenFile>> rs = new LinkedHashMap<>();

        for (GenFile genFile : genFiles) {
            if (rs.containsKey(genFile.getRelId()) == false) {
                rs.put(genFile.getRelId(), new LinkedHashMap<>());
            }

            rs.get(genFile.getRelId()).put(genFile.getFileNo() + "", genFile);
        }

        return rs;
    }

    public void changeInputFileRelIds(Map<String, Object> param, int id) {
        String genFileIdsStr = Util.ifEmpty((String) param.get("genFileIdsStr"), null);

        if (genFileIdsStr != null) {
            List<Integer> genFileIds = Util.getListDividedBy(genFileIdsStr, ",");

            // 파일이 먼저 생성된 후에, 관련 데이터가 생성되는 경우에는, file의 relId가 일단 0으로 저장된다.
            // 그것을 뒤늦게라도 이렇게 고처야 한다.
            for (int genFileId : genFileIds) {
                changeRelId(genFileId, id);
            }
        }
    }

}
