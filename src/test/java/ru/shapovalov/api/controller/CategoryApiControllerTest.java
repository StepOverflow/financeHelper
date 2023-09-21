package ru.shapovalov.api.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shapovalov.MockSecurityConfiguration;
import ru.shapovalov.SecurityConfiguration;
import ru.shapovalov.service.CategoryDto;
import ru.shapovalov.service.CategoryService;
import ru.shapovalov.service.UserDto;
import ru.shapovalov.service.UserService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryApiController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)

public class CategoryApiControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;

    @MockBean
    CategoryService categoryService;

    @Before
    public void setUp() {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.currentUser()).thenReturn(userDto);
    }

    @Test
    public void testGetAllByUserId() throws Exception {
        mockMvc.perform(post("/api/categories/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testCreateCategory() throws Exception {
        when(categoryService.create("NewCategoryName", 1L))
                .thenReturn(
                        new CategoryDto()
                                .setName("NewCategoryName")
                                .setId(1L)
                );
        mockMvc.perform(post("/api/categories/create")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"name\": \"NewCategoryName\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").value("NewCategoryName"));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        when(categoryService.delete(eq(1L), eq(1L))).thenReturn(true);

        mockMvc.perform(delete("/api/categories/delete")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"categoryId\": \"1\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("deleted").value(true));
    }

    @Test
    public void testEditCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto()
                .setId(1L)
                .setName("Name");
        when(categoryService.edit(eq(1L), eq("NewName"), eq(1L))).thenReturn(categoryDto.setName("NewName"));

        mockMvc.perform(post("/api/categories/edit")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"id\": \"1\",\n" +
                                "  \"name\" : \"NewName\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("NewName"));
    }
}