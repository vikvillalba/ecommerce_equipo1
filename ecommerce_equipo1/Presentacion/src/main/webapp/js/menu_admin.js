/**
 * funciones menu mobile
 */
document.addEventListener('DOMContentLoaded', function() {
    const navToggle = document.getElementById('navToggle');
    const mobileNav = document.getElementById('mobileNav');
    const mobileNavClose = document.getElementById('mobileNavClose');
    const mobileNavOverlay = document.getElementById('mobileNavOverlay');

    if (!navToggle || !mobileNav) {
        return;
    }

    // Abrir menú móvil
    navToggle.addEventListener('click', function() {
        openMobileMenu();
    });

    // Cerrar menú con botón de cierre
    if (mobileNavClose) {
        mobileNavClose.addEventListener('click', function() {
            closeMobileMenu();
        });
    }

    if (mobileNavOverlay) {
        mobileNavOverlay.addEventListener('click', function() {
            closeMobileMenu();
        });
    }

    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            closeMobileMenu();
        }
    });

    // Cerrar menú al hacer clic en un enlace
    const mobileNavLinks = mobileNav.querySelectorAll('.mobile-nav__link');
    mobileNavLinks.forEach(function(link) {
        link.addEventListener('click', function() {
            closeMobileMenu();
        });
    });

    // Funciones auxiliares
    function openMobileMenu() {
        mobileNav.classList.add('show');
        navToggle.setAttribute('aria-expanded', 'true');
        document.body.style.overflow = 'hidden';
    }

    function closeMobileMenu() {
        mobileNav.classList.remove('show');
        navToggle.setAttribute('aria-expanded', 'false');
        document.body.style.overflow = '';
    }
});
