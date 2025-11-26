<%-- 
    Document   : login
    Created on : Nov 20, 2025, 8:17:26 PM
    Author     : Maryr
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="jspf/header_cliente.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Catálogo de productos</title>
        <link rel="stylesheet" href="CSS/estiloCatalogo.css">
    </head>
    <body>
        <%
            List<Producto> todosLosProductos = (List<Producto>) request.getAttribute("productos");
            if (todosLosProductos == null) {
                todosLosProductos = new ArrayList<Producto>();
            }

            String categoriaFiltro = request.getParameter("categoria");
            if (categoriaFiltro == null || categoriaFiltro.isEmpty()) {
                categoriaFiltro = "todas";
            }

            String precioMinStr = request.getParameter("precioMin");
            String precioMaxStr = request.getParameter("precioMax");

            double precioMin = 0;
            double precioMax = 500;

            if (precioMinStr != null && !precioMinStr.isEmpty()) {
                try {
                    precioMin = Double.parseDouble(precioMinStr);
                } catch (NumberFormatException e) {
                    precioMin = 0;
                }
            }

            if (precioMaxStr != null && !precioMaxStr.isEmpty()) {
                try {
                    precioMax = Double.parseDouble(precioMaxStr);
                } catch (NumberFormatException e) {
                    precioMax = 500;
                }
            }

            List<Producto> productosFiltrados = new ArrayList<Producto>();
            for (int i = 0; i < todosLosProductos.size(); i++) {
                Producto p = todosLosProductos.get(i);
                boolean cumpleCategoria = false;
                if (categoriaFiltro.equals("todas")) {
                    cumpleCategoria = true;
                } else if (p.getCategoria() != null) {
                    cumpleCategoria = p.getCategoria().toString().equals(categoriaFiltro);
                }

                boolean cumplePrecio = p.getPrecio() >= precioMin && p.getPrecio() <= precioMax;

                if (cumpleCategoria && cumplePrecio) {
                    productosFiltrados.add(p);
                }
            }
        %>

        <div class="page-container">
            <aside class="sidebar">
                <div class="filter-header">
                    <span>Filtrar</span>
                </div>

                <form action="CatalogoServlet" method="GET">
                    <div class="filter-section">
                        <h3 class="section-title">Categorías</h3>
                        <div class="category-list">
                            <label class="category-item <%= categoriaFiltro.equals("todas") ? "active" : ""%>">
                                <input type="radio" name="categoria" value="todas" 
                                       <%= categoriaFiltro.equals("todas") ? "checked" : ""%>
                                       style="display: none;">
                                Todas las categorías
                            </label>
                            <label class="category-item <%= categoriaFiltro.equals("ACCESORIOS") ? "active" : ""%>">
                                <input type="radio" name="categoria" value="ACCESORIOS" 
                                       <%= categoriaFiltro.equals("ACCESORIOS") ? "checked" : ""%>
                                       style="display: none;">
                                Accesorios
                            </label>
                            <label class="category-item <%= categoriaFiltro.equals("REBAJAS") ? "active" : ""%>">
                                <input type="radio" name="categoria" value="REBAJAS" 
                                       <%= categoriaFiltro.equals("REBAJAS") ? "checked" : ""%>
                                       style="display: none;">
                                Rebajas
                            </label>
                            <label class="category-item <%= categoriaFiltro.equals("ROPA_CAMA") ? "active" : ""%>">
                                <input type="radio" name="categoria" value="ROPA_CAMA" 
                                       <%= categoriaFiltro.equals("ROPA_CAMA") ? "checked" : ""%>
                                       style="display: none;">
                                Ropa de cama
                            </label>
                            <label class="category-item <%= categoriaFiltro.equals("VESTIDOS") ? "active" : ""%>">
                                <input type="radio" name="categoria" value="VESTIDOS" 
                                       <%= categoriaFiltro.equals("VESTIDOS") ? "checked" : ""%>
                                       style="display: none;">
                                Vestidos
                            </label>
                            <label class="category-item <%= categoriaFiltro.equals("NINOS") ? "active" : ""%>">
                                <input type="radio" name="categoria" value="NINOS" 
                                       <%= categoriaFiltro.equals("NINOS") ? "checked" : ""%>
                                       style="display: none;">
                                Niños
                            </label>
                        </div>
                    </div>

                    <div class="filter-section">
                        <h3 class="section-title">Precio</h3>
                        <div class="price-filter">
                            <div class="price-labels">
                                <span id="minLabel">$<%= (int) precioMin%></span>
                                <span id="maxLabel">$<%= (int) precioMax%></span>
                            </div>
                            <div class="slider-container">
                                <div class="slider-track"></div>
                                <div class="slider-range" id="range"></div>
                                <input type="range" name="precioMin" id="min" min="0" max="500" 
                                       value="<%= (int) precioMin%>" step="10" oninput="updateRange()">
                                <input type="range" name="precioMax" id="max" min="0" max="500" 
                                       value="<%= (int) precioMax%>" step="10" oninput="updateRange()">
                            </div>
                            <button type="submit" class="btn-filter">Filtrar</button>
                        </div>
                    </div>
                </form>
            </aside>

            <main class="main-content">
                <h1 class="page-title">.</h1>

                <% if (productosFiltrados.size() > 0) { %>
                <div class="products-grid">
                    <% for (int i = 0; i < productosFiltrados.size(); i++) {
                            Producto producto = productosFiltrados.get(i);
                    %>
                    <div class="product-card">
                        <% if (!producto.isDisponibilidad()) { %>
                        <div class="badge-no-disponible">No disponible</div>
                        <% }%>

                        <div class="product-image">
                            <img src="<%= producto.getImagen()%>" 
                                 alt="<%= producto.getNombre()%>"
                                 onerror="this.src='img/placeholder.jpg'">

                            <div class="product-overlay">
                                <a href="Producto?id=<%= producto.getId()%>" class="btn-view">Ver más</a>
                            </div>
                        </div>

                        <div class="product-info">
                            <h3 class="product-name"><%= producto.getNombre()%></h3>
                            <p class="product-price">$<%= String.format("%.2f", producto.getPrecio())%></p>
                        </div>
                    </div>
                    <% } %>
                </div>
                <% } else { %>
                <div class="no-products">
                    <p>No se encontraron productos con los filtros seleccionados</p>
                </div>
                <% }%>
            </main>
        </div>
        <script>
            function updateRange() {
                const min = parseInt(document.getElementById('min').value);
                const max = parseInt(document.getElementById('max').value);

                document.getElementById('minLabel').textContent = '$' + min;
                document.getElementById('maxLabel').textContent = '$' + max;

                const range = document.getElementById('range');
                range.style.left = (min / 5) + '%';
                range.style.width = ((max - min) / 5) + '%';
            }
            updateRange();
        </script>
    </body>
</html>
<%@ include file="jspf/footer.jspf" %>