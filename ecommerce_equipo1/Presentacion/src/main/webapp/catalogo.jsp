<%-- 
    Document   : login
    Created on : Nov 20, 2025, 8:17:26 PM
    Author     : Maryr
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Catálogo de productos - Sweet Blossom</title>
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
                boolean cumpleCategoria = categoriaFiltro.equals("todas") || p.getCategoria().toString().equals(categoriaFiltro);
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
                            <label class="category-item <%= categoriaFiltro.equals("todas") ? "active" : "" %>">
                                <input type="radio" name="categoria" value="todas" 
                                       <%= categoriaFiltro.equals("todas") ? "checked" : "" %>
                                       onchange="this.form.submit()" style="display: none;">
                                Todas las categorías
                            </label>
                            <label class="category-item <%= categoriaFiltro.equals("ACCESORIOS") ? "active" : "" %>">
                                <input type="radio" name="categoria" value="ACCESORIOS" 
                                       <%= categoriaFiltro.equals("ACCESORIOS") ? "checked" : "" %>
                                       onchange="this.form.submit()" style="display: none;">
                                Accesorios
                            </label>
                            <label class="category-item <%= categoriaFiltro.equals("REBAJAS") ? "active" : "" %>">
                                <input type="radio" name="categoria" value="REBAJAS" 
                                       <%= categoriaFiltro.equals("REBAJAS") ? "checked" : "" %>
                                       onchange="this.form.submit()" style="display: none;">
                                Rebajas
                            </label>
                            <label class="category-item <%= categoriaFiltro.equals("ROPA_CAMA") ? "active" : "" %>">
                                <input type="radio" name="categoria" value="ROPA_CAMA" 
                                       <%= categoriaFiltro.equals("ROPA_CAMA") ? "checked" : "" %>
                                       onchange="this.form.submit()" style="display: none;">
                                Ropa de cama
                            </label>
                            <label class="category-item <%= categoriaFiltro.equals("VESTIDOS") ? "active" : "" %>">
                                <input type="radio" name="categoria" value="VESTIDOS" 
                                       <%= categoriaFiltro.equals("VESTIDOS") ? "checked" : "" %>
                                       onchange="this.form.submit()" style="display: none;">
                                Vestidos
                            </label>
                            <label class="category-item <%= categoriaFiltro.equals("NINOS") ? "active" : "" %>">
                                <input type="radio" name="categoria" value="NINOS" 
                                       <%= categoriaFiltro.equals("NINOS") ? "checked" : "" %>
                                       onchange="this.form.submit()" style="display: none;">
                                Niños
                            </label>
                        </div>
                    </div>

                    <div class="filter-section">
                        <h3 class="section-title">Precio</h3>
                        <div class="price-filter">
                            <div class="price-labels">
                                <span class="price-label">$<%= (int)precioMin %></span>
                                <span class="price-label">$<%= (int)precioMax %></span>
                            </div>
                            <div class="form-group">
                                <label>Mínimo: $<%= (int)precioMin %></label>
                                <input type="range" name="precioMin" min="0" max="500" 
                                       value="<%= (int)precioMin %>" step="10">
                            </div>
                            <div class="form-group">
                                <label>Máximo: $<%= (int)precioMax %></label>
                                <input type="range" name="precioMax" min="0" max="500" 
                                       value="<%= (int)precioMax %>" step="10">
                            </div>
                            <button type="submit" class="btn-filter">Filtrar</button>
                        </div>
                    </div>
                </form>
            </aside>

            <main class="main-content">
                <h1 class="page-title">Catálogo de productos</h1>

                <% if (productosFiltrados.size() > 0) { %>
                    <div class="products-grid">
                        <% for (int i = 0; i < productosFiltrados.size(); i++) {
                            Producto producto = productosFiltrados.get(i);
                        %>
                            <div class="product-card">
                                <% if (!producto.isDisponibilidad()) { %>
                                    <div class="badge-no-disponible">No disponible</div>
                                <% } %>

                                <div class="product-image">
                                    <img src="<%= producto.getImagen() %>" 
                                         alt="<%= producto.getNombre() %>"
                                         onerror="this.src='img/placeholder.jpg'">

                                    <div class="product-overlay">
                                        <a href="Producto?id=<%= producto.getId() %>" class="btn-view">Ver más</a>
                                    </div>
                                </div>

                                <div class="product-info">
                                    <h3 class="product-name"><%= producto.getNombre() %></h3>
                                    <p class="product-price">$<%= String.format("%.2f", producto.getPrecio()) %></p>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } else { %>
                    <div class="no-products">
                        <p>No se encontraron productos con los filtros seleccionados</p>
                    </div>
                <% } %>
            </main>
        </div>
    </body>
</html>