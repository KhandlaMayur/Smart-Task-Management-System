// =========================================
// FORM VALIDATION SCRIPT
// =========================================

// PAGE LOAD

document.addEventListener("DOMContentLoaded", () => {

    initializeValidation();

});

// =========================================
// INITIALIZE VALIDATION
// =========================================

function initializeValidation() {

    validateRegisterForm();

    validateLoginForm();

    validateTaskForm();

    validateCategoryForm();

    validateProfileForm();

    validatePasswordMatch();

}

// =========================================
// REGISTER FORM VALIDATION
// =========================================

function validateRegisterForm() {

    const registerForm =
        document.getElementById("registerForm");

    if (!registerForm) {

        return;
    }

    registerForm.addEventListener("submit", (event) => {

        let isValid = true;

        // NAME

        const name =
            document.getElementById("name");

        if (!validateName(name.value)) {

            showError(
                name,
                "Enter Valid Name"
            );

            isValid = false;

        } else {

            removeError(name);
        }

        // EMAIL

        const email =
            document.getElementById("email");

        if (!validateEmail(email.value)) {

            showError(
                email,
                "Enter Valid Email"
            );

            isValid = false;

        } else {

            removeError(email);
        }

        // PASSWORD

        const password =
            document.getElementById("password");

        if (!validatePassword(password.value)) {

            showError(
                password,
                "Password Must Be Strong"
            );

            isValid = false;

        } else {

            removeError(password);
        }

        // CONFIRM PASSWORD

        const confirmPassword =
            document.getElementById("confirmPassword");

        if (password.value !== confirmPassword.value) {

            showError(
                confirmPassword,
                "Passwords Do Not Match"
            );

            isValid = false;

        } else {

            removeError(confirmPassword);
        }

        if (!isValid) {

            event.preventDefault();
        }

    });
}

// =========================================
// LOGIN FORM VALIDATION
// =========================================

function validateLoginForm() {

    const loginForm =
        document.getElementById("loginForm");

    if (!loginForm) {

        return;
    }

    loginForm.addEventListener("submit", (event) => {

        let isValid = true;

        // EMAIL

        const email =
            document.getElementById("email");

        if (!validateEmail(email.value)) {

            showError(
                email,
                "Enter Valid Email"
            );

            isValid = false;

        } else {

            removeError(email);
        }

        // PASSWORD

        const password =
            document.getElementById("password");

        if (password.value.trim() === "") {

            showError(
                password,
                "Password Required"
            );

            isValid = false;

        } else {

            removeError(password);
        }

        if (!isValid) {

            event.preventDefault();
        }

    });
}

// =========================================
// TASK FORM VALIDATION
// =========================================

function validateTaskForm() {

    const taskForm =
        document.getElementById("taskForm");

    if (!taskForm) {

        return;
    }

    taskForm.addEventListener("submit", (event) => {

        let isValid = true;

        // TITLE

        const title =
            document.getElementById("title");

        if (title.value.trim().length < 3) {

            showError(
                title,
                "Task Title Minimum 3 Characters"
            );

            isValid = false;

        } else {

            removeError(title);
        }

        // DESCRIPTION

        const description =
            document.getElementById("description");

        if (description.value.trim().length < 5) {

            showError(
                description,
                "Description Minimum 5 Characters"
            );

            isValid = false;

        } else {

            removeError(description);
        }

        // DUE DATE

        const dueDate =
            document.getElementById("dueDate");

        if (dueDate.value === "") {

            showError(
                dueDate,
                "Select Due Date"
            );

            isValid = false;

        } else {

            removeError(dueDate);
        }

        if (!isValid) {

            event.preventDefault();
        }

    });
}

// =========================================
// CATEGORY VALIDATION
// =========================================

function validateCategoryForm() {

    const categoryForm =
        document.getElementById("categoryForm");

    if (!categoryForm) {

        return;
    }

    categoryForm.addEventListener("submit", (event) => {

        const categoryName =
            document.getElementById("categoryName");

        if (categoryName.value.trim().length < 2) {

            showError(
                categoryName,
                "Category Name Too Short"
            );

            event.preventDefault();

        } else {

            removeError(categoryName);
        }

    });
}

// =========================================
// PROFILE FORM VALIDATION
// =========================================

function validateProfileForm() {

    const profileForm =
        document.getElementById("profileForm");

    if (!profileForm) {

        return;
    }

    profileForm.addEventListener("submit", (event) => {

        let isValid = true;

        const name =
            document.getElementById("name");

        const email =
            document.getElementById("email");

        if (!validateName(name.value)) {

            showError(
                name,
                "Invalid Name"
            );

            isValid = false;

        } else {

            removeError(name);
        }

        if (!validateEmail(email.value)) {

            showError(
                email,
                "Invalid Email"
            );

            isValid = false;

        } else {

            removeError(email);
        }

        if (!isValid) {

            event.preventDefault();
        }

    });
}

// =========================================
// PASSWORD MATCH VALIDATION
// =========================================

function validatePasswordMatch() {

    const confirmPassword =
        document.getElementById("confirmPassword");

    if (!confirmPassword) {

        return;
    }

    confirmPassword.addEventListener("keyup", () => {

        const password =
            document.getElementById("password");

        if (password.value !== confirmPassword.value) {

            confirmPassword.classList.add("is-invalid");

        } else {

            confirmPassword.classList.remove("is-invalid");

            confirmPassword.classList.add("is-valid");
        }

    });
}

// =========================================
// EMAIL VALIDATION
// =========================================

function validateEmail(email) {

    const regex =
        /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    return regex.test(email);
}

// =========================================
// PASSWORD VALIDATION
// =========================================

function validatePassword(password) {

    const regex =
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!]).{8,}$/;

    return regex.test(password);
}

// =========================================
// NAME VALIDATION
// =========================================

function validateName(name) {

    const regex =
        /^[A-Za-z ]{2,50}$/;

    return regex.test(name);
}

// =========================================
// SHOW ERROR
// =========================================

function showError(input, message) {

    input.classList.add("is-invalid");

    input.classList.remove("is-valid");

    let error =
        input.nextElementSibling;

    if (!error
        || !error.classList.contains("invalid-feedback")) {

        error =
            document.createElement("div");

        error.className =
            "invalid-feedback";

        input.parentNode.appendChild(error);
    }

    error.innerText = message;
}

// =========================================
// REMOVE ERROR
// =========================================

function removeError(input) {

    input.classList.remove("is-invalid");

    input.classList.add("is-valid");
}