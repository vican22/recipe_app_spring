package com.vedran.recipe.controllers;

import com.vedran.recipe.dto.RecipeDto;
import com.vedran.recipe.services.ImageService;
import com.vedran.recipe.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeDtoById(id));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable Long id, @RequestParam("imageFile")MultipartFile file) {

        imageService.saveImageFile(id, file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException {
        RecipeDto recipeDto = recipeService.findRecipeDtoById(id);

        if (recipeDto.getImage() != null) {
            byte[] byteArray = new byte[recipeDto.getImage().length];
            int i = 0;

            for (byte b : recipeDto.getImage()) {
                byteArray[i++] = b;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
