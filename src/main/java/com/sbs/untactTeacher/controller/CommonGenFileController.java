package com.sbs.untactTeacher.controller;

import com.sbs.untactTeacher.dto.GenFile;
import com.sbs.untactTeacher.dto.ResultData;
import com.sbs.untactTeacher.exception.GenFileNotFoundException;
import com.sbs.untactTeacher.service.GenFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@Controller
public class CommonGenFileController {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    @Autowired
    private GenFileService genFileService;

    @RequestMapping("/common/genFile/doUpload")
    @ResponseBody
    public ResultData doUpload(@RequestParam Map<String, Object> param, MultipartRequest multipartRequest) {
        return genFileService.saveFiles(param, multipartRequest);
    }

    @GetMapping("/common/genFile/doDownload")
    public ResponseEntity<Resource> downloadFile(int id, HttpServletRequest request) throws IOException {
        GenFile genFile = genFileService.getGenFile(id);

        if (genFile == null) {
            throw new GenFileNotFoundException();
        }

        String filePath = genFile.getFilePath(genFileDirPath);

        Resource resource = new InputStreamResource(new FileInputStream(filePath));

        // Try to determine file's content type
        String contentType = request.getServletContext().getMimeType(new File(filePath).getAbsolutePath());

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + genFile.getOriginFileName() + "\"")
                .contentType(MediaType.parseMediaType(contentType)).body(resource);
    }

    @GetMapping("/common/genFile/file/{relTypeCode}/{relId}/{typeCode}/{type2Code}/{fileNo}")
    public ResponseEntity<Resource> showFile(HttpServletRequest request, @PathVariable String relTypeCode,
                                             @PathVariable int relId, @PathVariable String typeCode, @PathVariable String type2Code,
                                             @PathVariable int fileNo) throws FileNotFoundException {
        GenFile genFile = genFileService.getGenFile(relTypeCode, relId, typeCode, type2Code, fileNo);

        if (genFile == null) {
            throw new GenFileNotFoundException();
        }

        String filePath = genFile.getFilePath(genFileDirPath);
        Resource resource = new InputStreamResource(new FileInputStream(filePath));

        // Try to determine file's content type
        String contentType = request.getServletContext().getMimeType(new File(filePath).getAbsolutePath());

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
    }
}
