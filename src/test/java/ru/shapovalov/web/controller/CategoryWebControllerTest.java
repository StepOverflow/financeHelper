package ru.shapovalov.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shapovalov.MockSecurityConfiguration;
import ru.shapovalov.SecurityConfiguration;
import ru.shapovalov.service.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryWebController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
public class CategoryWebControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @MockBean
    UserService userService;

    @WithMockUser
    @Test
    public void getCategoryList() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");
        List<CategoryDto> categories = new ArrayList<>();

        when(userService.currentUser()).thenReturn(userDto);
        when(categoryService.getAll(userDto.getId())).thenReturn(categories);

        mockMvc.perform(get("/categories/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categories", categories))
                .andExpect(view().name("category-list"));
    }

    @WithMockUser
    @Test
    public void getCreateCategory() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.currentUser()).thenReturn(userDto);

        mockMvc.perform(get("/categories/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("category-create"));
    }

    @WithMockUser
    @Test
    public void testPostCreateAccount() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.currentUser()).thenReturn(userDto);

        mockMvc.perform(post("/categories/create")
                        .param("name", "CategoryName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories/list"));

        verify(categoryService, times(1)).create("CategoryName", userDto.getId());
    }
}