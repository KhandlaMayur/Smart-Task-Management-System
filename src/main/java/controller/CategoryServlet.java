package controller;

import dao.CategoryDAO;
import model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class CategoryServlet extends HttpServlet {

    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {

        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        // =========================================
        // SESSION VALIDATION
        // =========================================

        if (session == null || session.getAttribute("userId") == null) {

            response.sendRedirect("auth/login.jsp?error=Please Login First");
            return;
        }

        // =========================================
        // GET ACTION
        // =========================================

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {

            response.sendRedirect("category/category-list.jsp?error=Invalid Action");
            return;
        }

        switch (action) {

            case "add":
                addCategory(request, response);
                break;

            case "update":
                updateCategory(request, response);
                break;

            case "delete":
                deleteCategory(request, response);
                break;

            default:
                response.sendRedirect("category/category-list.jsp?error=Unknown Action");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    // =========================================
    // ADD CATEGORY
    // =========================================

    private void addCategory(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException {

        String categoryName = request.getParameter("categoryName");

        // =========================================
        // VALIDATION
        // =========================================

        if (categoryName == null || categoryName.trim().isEmpty()) {

            response.sendRedirect("category/add-category.jsp?error=Category Name Required");
            return;
        }

        // =========================================
        // CREATE OBJECT
        // =========================================

        Category category = new Category();

        category.setCategoryName(categoryName);

        // =========================================
        // SAVE CATEGORY
        // =========================================

        boolean isAdded = categoryDAO.addCategory(category);

        if (isAdded) {

            response.sendRedirect("category/category-list.jsp?success=Category Added Successfully");

        } else {

            response.sendRedirect("category/add-category.jsp?error=Failed To Add Category");
        }
    }

    // =========================================
    // UPDATE CATEGORY
    // =========================================

    private void updateCategory(HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException {

        String idStr = request.getParameter("id");
        String categoryName = request.getParameter("categoryName");

        // =========================================
        // VALIDATION
        // =========================================

        if (idStr == null || categoryName == null
                || idStr.trim().isEmpty()
                || categoryName.trim().isEmpty()) {

            response.sendRedirect("category/category-list.jsp?error=Invalid Input");
            return;
        }

        int id = Integer.parseInt(idStr);

        // =========================================
        // CREATE OBJECT
        // =========================================

        Category category = new Category();

        category.setId(id);
        category.setCategoryName(categoryName);

        // =========================================
        // UPDATE CATEGORY
        // =========================================

        boolean isUpdated = categoryDAO.updateCategory(category);

        if (isUpdated) {

            response.sendRedirect("category/category-list.jsp?success=Category Updated Successfully");

        } else {

            response.sendRedirect("category/category-list.jsp?error=Failed To Update Category");
        }
    }

    // =========================================
    // DELETE CATEGORY
    // =========================================

    private void deleteCategory(HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException {

        String idStr = request.getParameter("id");

        // =========================================
        // VALIDATION
        // =========================================

        if (idStr == null || idStr.trim().isEmpty()) {

            response.sendRedirect("category/category-list.jsp?error=Invalid Category ID");
            return;
        }

        int id = Integer.parseInt(idStr);

        // =========================================
        // DELETE CATEGORY
        // =========================================

        boolean isDeleted = categoryDAO.deleteCategory(id);

        if (isDeleted) {

            response.sendRedirect("category/category-list.jsp?success=Category Deleted Successfully");

        } else {

            response.sendRedirect("category/category-list.jsp?error=Failed To Delete Category");
        }
    }
}
