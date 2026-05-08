// =========================================
// MAIN APPLICATION SCRIPT
// =========================================

document.addEventListener("DOMContentLoaded", () => {

    initializeApplication();

});

// =========================================
// INITIALIZE APPLICATION
// =========================================

function initializeApplication() {

    initializeSidebar();

    initializeDropdowns();

    initializeAlerts();

    initializeFormValidation();

    initializeTooltips();

    initializePopovers();

    initializeFileUploadPreview();

    initializePasswordToggle();

    initializeSmoothScroll();

    initializeLoader();
}

// =========================================
// SIDEBAR TOGGLE
// =========================================

function initializeSidebar() {

    const sidebarToggle =
        document.getElementById("sidebarToggle");

    const sidebar =
        document.querySelector(".sidebar-wrapper");

    if (!sidebarToggle || !sidebar) {

        return;
    }

    sidebarToggle.addEventListener("click", () => {

        sidebar.classList.toggle("active");
    });
}

// =========================================
// BOOTSTRAP DROPDOWNS
// =========================================

function initializeDropdowns() {

    const dropdownElementList =
        [].slice.call(
            document.querySelectorAll(".dropdown-toggle")
        );

    dropdownElementList.map((dropdownToggleEl) => {

        return new bootstrap.Dropdown(
            dropdownToggleEl
        );
    });
}

// =========================================
// AUTO CLOSE ALERTS
// =========================================

function initializeAlerts() {

    const alerts =
        document.querySelectorAll(".alert");

    alerts.forEach((alert) => {

        setTimeout(() => {

            alert.classList.remove("show");

            alert.classList.add("fade");

            setTimeout(() => {

                alert.remove();

            }, 500);

        }, 4000);
    });
}

// =========================================
// FORM VALIDATION
// =========================================

function initializeFormValidation() {

    const forms =
        document.querySelectorAll(".needs-validation");

    Array.from(forms).forEach((form) => {

        form.addEventListener("submit", (event) => {

            if (!form.checkValidity()) {

                event.preventDefault();

                event.stopPropagation();
            }

            form.classList.add("was-validated");

        }, false);
    });
}

// =========================================
// BOOTSTRAP TOOLTIPS
// =========================================

function initializeTooltips() {

    const tooltipTriggerList =
        [].slice.call(
            document.querySelectorAll(
                '[data-bs-toggle="tooltip"]'
            )
        );

    tooltipTriggerList.map((tooltipTriggerEl) => {

        return new bootstrap.Tooltip(
            tooltipTriggerEl
        );
    });
}

// =========================================
// BOOTSTRAP POPOVERS
// =========================================

function initializePopovers() {

    const popoverTriggerList =
        [].slice.call(
            document.querySelectorAll(
                '[data-bs-toggle="popover"]'
            )
        );

    popoverTriggerList.map((popoverTriggerEl) => {

        return new bootstrap.Popover(
            popoverTriggerEl
        );
    });
}

// =========================================
// FILE UPLOAD PREVIEW
// =========================================

function initializeFileUploadPreview() {

    const fileInput =
        document.getElementById("fileUpload");

    const previewImage =
        document.getElementById("previewImage");

    if (!fileInput || !previewImage) {

        return;
    }

    fileInput.addEventListener("change", (event) => {

        const file =
            event.target.files[0];

        if (file) {

            const reader =
                new FileReader();

            reader.onload = (e) => {

                previewImage.src =
                    e.target.result;

                previewImage.style.display =
                    "block";
            };

            reader.readAsDataURL(file);
        }
    });
}

// =========================================
// PASSWORD SHOW HIDE
// =========================================

function initializePasswordToggle() {

    const toggleButtons =
        document.querySelectorAll(".toggle-password");

    toggleButtons.forEach((button) => {

        button.addEventListener("click", () => {

            const target =
                document.querySelector(
                    button.dataset.target
                );

            if (!target) {

                return;
            }

            if (target.type === "password") {

                target.type = "text";

                button.innerHTML =
                    '<i class="fa-solid fa-eye-slash"></i>';

            } else {

                target.type = "password";

                button.innerHTML =
                    '<i class="fa-solid fa-eye"></i>';
            }
        });
    });
}

// =========================================
// SMOOTH SCROLL
// =========================================

function initializeSmoothScroll() {

    const links =
        document.querySelectorAll(
            'a[href^="#"]'
        );

    links.forEach((link) => {

        link.addEventListener("click", (e) => {

            e.preventDefault();

            const targetId =
                link.getAttribute("href");

            const targetElement =
                document.querySelector(targetId);

            if (targetElement) {

                targetElement.scrollIntoView({

                    behavior: "smooth"
                });
            }
        });
    });
}

// =========================================
// PAGE LOADER
// =========================================

function initializeLoader() {

    const loader =
        document.getElementById("pageLoader");

    if (!loader) {

        return;
    }

    window.addEventListener("load", () => {

        loader.style.opacity = "0";

        setTimeout(() => {

            loader.style.display = "none";

        }, 500);
    });
}

// =========================================
// CONFIRM DELETE
// =========================================

function confirmDelete(message) {

    return confirm(
        message || "Are You Sure?"
    );
}

// =========================================
// SHOW TOAST MESSAGE
// =========================================

function showToast(message,
                   type = "success") {

    const toastContainer =
        document.getElementById("toastContainer");

    if (!toastContainer) {

        return;
    }

    const toast =
        document.createElement("div");

    toast.className =
        `toast align-items-center text-bg-${type} border-0 show mb-2`;

    toast.innerHTML =
        `
        <div class="d-flex">

            <div class="toast-body">

                ${message}

            </div>

            <button type="button"
                    class="btn-close btn-close-white me-2 m-auto"
                    data-bs-dismiss="toast">
            </button>

        </div>
        `;

    toastContainer.appendChild(toast);

    setTimeout(() => {

        toast.remove();

    }, 4000);
}