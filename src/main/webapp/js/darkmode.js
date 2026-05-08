// =========================================
// DARK MODE SCRIPT
// =========================================

// GET DARK MODE BUTTON

const darkModeToggle =
    document.getElementById("darkModeToggle");

// =========================================
// APPLY SAVED THEME
// =========================================

window.addEventListener("load", () => {

    const savedTheme =
        localStorage.getItem("theme");

    if (savedTheme === "dark") {

        document.body.classList.add("dark-mode");

        updateDarkModeIcon(true);
    }
});

// =========================================
// TOGGLE DARK MODE
// =========================================

if (darkModeToggle) {

    darkModeToggle.addEventListener("click", () => {

        document.body.classList.toggle("dark-mode");

        // CHECK MODE

        const isDarkMode =
            document.body.classList.contains("dark-mode");

        // SAVE MODE

        if (isDarkMode) {

            localStorage.setItem("theme", "dark");

        } else {

            localStorage.setItem("theme", "light");
        }

        // UPDATE ICON

        updateDarkModeIcon(isDarkMode);
    });
}

// =========================================
// UPDATE BUTTON ICON
// =========================================

function updateDarkModeIcon(isDarkMode) {

    if (!darkModeToggle) {

        return;
    }

    if (isDarkMode) {

        darkModeToggle.innerHTML =
            '<i class="fa-solid fa-sun"></i>';

    } else {

        darkModeToggle.innerHTML =
            '<i class="fa-solid fa-moon"></i>';
    }
}

// =========================================
// OPTIONAL AUTO DETECT SYSTEM THEME
// =========================================

const prefersDarkMode =
    window.matchMedia("(prefers-color-scheme: dark)");

if (!localStorage.getItem("theme")) {

    if (prefersDarkMode.matches) {

        document.body.classList.add("dark-mode");

        updateDarkModeIcon(true);
    }
}

// =========================================
// SYSTEM THEME CHANGE LISTENER
// =========================================

prefersDarkMode.addEventListener("change", (event) => {

    if (!localStorage.getItem("theme")) {

        if (event.matches) {

            document.body.classList.add("dark-mode");

            updateDarkModeIcon(true);

        } else {

            document.body.classList.remove("dark-mode");

            updateDarkModeIcon(false);
        }
    }
});