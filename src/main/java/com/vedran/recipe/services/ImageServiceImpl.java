package com.vedran.recipe.services;

import com.vedran.recipe.models.Recipe;
import com.vedran.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {

        try {
            Recipe recipe = recipeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Recipe Not Found"));

            Byte[] byteObject = new Byte[file.getBytes().length];

            int i = 0;


            for (byte b : file.getBytes()) {
                byteObject[i++] = b;
            }

            recipe.setImage(byteObject);

            recipeRepository.save(recipe);
        } catch (IOException e) {
            //todo handle better
            e.printStackTrace();
        }


    }
}
