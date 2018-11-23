package com.example.controller;

import com.example.domain.Chapter;
import com.example.service.impl.ChapterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class LearningController {
    private final ChapterService chapterService;

    public LearningController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GET
    @Path("/chapters")
    public Response findAllChapters(){
        return Response.ok(chapterService.getAllChapters()).build();
    }

    @POST
    @Path("/chapters")
    public Response createChapter(Chapter chapter){
        chapterService.create(chapter);
        return Response.ok().build();
    }

    @GET
    @Path("/chapters/{id}")
    public Response findChapter(@PathParam("id") Integer id){
        Chapter chapter = chapterService.getChapter(id);
        if(chapter == null){
            throw new NotFoundException("Chapter not found");
        }
        return Response.ok(chapter).build();
    }

    @PUT
    @Path("/chapters/{id}")
    public Response updateChapter(@PathParam("id") Integer id, @RequestBody Chapter chapter){
        chapter.setId(id);
        chapterService.update(chapter);
        return Response.ok().build();
    }

    @DELETE
    @Path("/chapters/{id}")
    public Response deleteChapter(@PathParam("id") Integer id){
        chapterService.delete(id);
        return Response.ok().build();
    }


}
