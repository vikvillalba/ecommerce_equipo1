/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

/**
 * Funcionalidad del dropdown de cuenta de administrador
 */
document.addEventListener('DOMContentLoaded', function() {
    const dropdownToggle = document.getElementById('accountDropdown');
    const dropdownMenu = document.getElementById('dropdownMenu');

    if (!dropdownToggle || !dropdownMenu) {
        return; // Si no existen los elementos, salir
    }

    // Toggle del dropdown al hacer clic en el botón
    dropdownToggle.addEventListener('click', function(e) {
        e.stopPropagation();
        const isExpanded = dropdownToggle.getAttribute('aria-expanded') === 'true';

        if (isExpanded) {
            closeDropdown();
        } else {
            openDropdown();
        }
    });

    // Cerrar dropdown al hacer clic fuera
    document.addEventListener('click', function(e) {
        if (!dropdownToggle.contains(e.target) && !dropdownMenu.contains(e.target)) {
            closeDropdown();
        }
    });

    // Cerrar dropdown al presionar Escape
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            closeDropdown();
        }
    });

    // Funciones auxiliares
    function openDropdown() {
        dropdownMenu.classList.add('show');
        dropdownToggle.setAttribute('aria-expanded', 'true');
        dropdownToggle.classList.add('active');
    }

    function closeDropdown() {
        dropdownMenu.classList.remove('show');
        dropdownToggle.setAttribute('aria-expanded', 'false');
        dropdownToggle.classList.remove('active');
    }
});
