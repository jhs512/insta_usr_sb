<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
const alertMsg = '${msg}'.trim();

if ( alertMsg ) {
	alert(alertMsg);
}

history.back();
</script>