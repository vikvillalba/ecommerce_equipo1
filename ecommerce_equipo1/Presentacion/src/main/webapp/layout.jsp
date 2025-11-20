<%-- 
    Document   : layout
    Created on : 19 nov 2025, 8:18:36 p.m.
    Author     : erika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>${titulo}</title>
        <link rel="stylesheet" href="styles.css">
    </head>

    <body>

        ¿

        <main class="page-content">
            <!-- Aquí se inserta el contenido dinámico -->
            <jsp:include page="${contenido}" />
        </main>

    </body>
</html>
