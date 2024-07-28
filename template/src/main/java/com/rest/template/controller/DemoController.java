package com.rest.template.controller;

import com.rest.template.model.Demo;
import com.rest.template.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;


@RestController
@RequestMapping("api/images")
@AllArgsConstructor
@Tag(
        name = "Demo API to upload image",
        description = "This is the description of Demo API to upload image"
)
public class DemoController extends BaseController{

    private final DemoService demoService;

    @PostMapping
    @Operation(
            description = "Post endpoint to upload image",
            summary = "Upload image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    buildResponse(
                            demoService.uploadImage(file)
                    )
            );
        } catch(BadRequestException e){
            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST).
                    body(buildResponse(null, e.getMessage()));
        } catch (IOException e){
            return ResponseEntity.
                    status(HttpStatusCode.valueOf(503)).
                    body(buildResponse(null, e.getMessage()));
        }
    }

    @GetMapping("/image")
    @Operation(
            description="Get endpoint to retrieve all the image in base64",
            summary="Retrieve all the image in Base64")
    public ResponseEntity<?>  getAllImage() throws
            DataFormatException, IOException {
        try {
            List<Demo> imageList = demoService.getAllImage();
            if (!imageList.isEmpty())
                return ResponseEntity.status(HttpStatus.OK)
                        .body(buildResponse(imageList));
            else
                return ResponseEntity.
                        status(HttpStatus.NOT_FOUND).
                        body(buildResponse(null, "Not Found"));
        }
        catch(ResourceAccessException e){
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(buildResponse(null, e.getMessage()));
        }
    }
}
