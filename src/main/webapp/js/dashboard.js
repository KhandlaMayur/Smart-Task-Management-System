// =========================================
// DASHBOARD SCRIPT
// =========================================

// PAGE LOADED

document.addEventListener("DOMContentLoaded", () => {

    initializeDashboard();

});

// =========================================
// INITIALIZE DASHBOARD
// =========================================

function initializeDashboard() {

    animateCards();

    initializeTooltips();

    autoHideAlerts();

    initializeTaskSearch();

    showCurrentDateTime();

    animateProgressBars();
}

// =========================================
// CARD ANIMATION
// =========================================

function animateCards() {

    const cards =
        document.querySelectorAll(".stat-card");

    cards.forEach((card, index) => {

        card.style.opacity = "0";

        card.style.transform = "translateY(20px)";

        setTimeout(() => {

            card.style.transition =
                "all 0.5s ease";

            card.style.opacity = "1";

            card.style.transform =
                "translateY(0)";

        }, index * 150);
    });
}

// =========================================
// INITIALIZE BOOTSTRAP TOOLTIPS
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
// AUTO HIDE ALERTS
// =========================================

function autoHideAlerts() {

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
// TASK SEARCH FILTER
// =========================================

function initializeTaskSearch() {

    const searchInput =
        document.getElementById("taskSearch");

    if (!searchInput) {

        return;
    }

    searchInput.addEventListener("keyup", () => {

        const filter =
            searchInput.value.toLowerCase();

        const rows =
            document.querySelectorAll(
                ".task-table tbody tr"
            );

        rows.forEach((row) => {

            const text =
                row.textContent.toLowerCase();

            if (text.includes(filter)) {

                row.style.display = "";

            } else {

                row.style.display = "none";
            }
        });
    });
}

// =========================================
// SHOW CURRENT DATE TIME
// =========================================

function showCurrentDateTime() {

    const dateTimeElement =
        document.getElementById("currentDateTime");

    if (!dateTimeElement) {

        return;
    }

    function updateDateTime() {

        const now =
            new Date();

        const formattedDate =
            now.toLocaleString();

        dateTimeElement.innerHTML =
            `<i class="fa-solid fa-calendar-days"></i>
             ${formattedDate}`;
    }

    updateDateTime();

    setInterval(updateDateTime, 1000);
}

// =========================================
// ANIMATE PROGRESS BARS
// =========================================

function animateProgressBars() {

    const progressBars =
        document.querySelectorAll(".progress-bar");

    progressBars.forEach((bar) => {

        const targetWidth =
            bar.style.width;

        bar.style.width = "0";

        setTimeout(() => {

            bar.style.transition =
                "width 1.5s ease";

            bar.style.width =
                targetWidth;

        }, 300);
    });
}

// =========================================
// CONFIRM DELETE
// =========================================

function confirmDelete(message) {

    return confirm(
        message || "Are You Sure To Delete?"
    );
}

// =========================================
// SHOW LOADING BUTTON
// =========================================

function showButtonLoading(button) {

    if (!button) {

        return;
    }

    button.disabled = true;

    button.innerHTML =
        `<span class="spinner-border spinner-border-sm"></span>
         Loading...`;
}

// =========================================
// RESET BUTTON
// =========================================

function resetButton(button, text) {

    if (!button) {

        return;
    }

    button.disabled = false;

    button.innerHTML = text;
}

// =========================================
// TOAST MESSAGE
// =========================================

function showToast(message, type = "success") {

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