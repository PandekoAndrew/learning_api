package com.example.controller;

import com.example.domain.Chapter;
import com.example.domain.Course;
import com.example.service.impl.ChapterService;
import com.example.service.impl.CourseService;
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
    private final CourseService courseService;

    public LearningController(ChapterService chapterService, CourseService courseService) {
        this.chapterService = chapterService;
        this.courseService = courseService;
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

    @GET
    @Path("/chapters/{chapterId}/courses/")
    public Response findCoursesOfChapter(@PathParam("chapterId") Integer id){
        return Response.ok(courseService.getCoursesByChapterId(id)).build();
    }

    @GET
    @Path("/courses/")
    public Response findCourses(){
        return Response.ok(courseService.getAllCourses()).build();
    }

    @GET
    @Path("/courses/{id}")
    public Response findCourse(@PathParam("id") Integer courseId){
        Course course = courseService.getCourse(courseId);
        if(course == null){
            throw new NotFoundException("Course not found");
        }
        return Response.ok(course).build();
    }

    @GET
    @Path("/chapters/{chapterId}/courses/{courseId}")
    public Response findCourse(@PathParam("chapterId") Integer chapterId, @PathParam("courseId") Integer courseId){
        Course course = courseService.getCourse(courseId);
        if(course == null){
            throw new NotFoundException("Course not found");
        }
        return Response.ok(course).build();
    }

    @POST
    @Path("/courses")
    public Response createCourse(@RequestBody Course course){
        courseService.create(course);
        return Response.ok().build();
    }

    @PUT
    @Path("/courses/{id}")
    public Response updateCourse(@PathParam("id") Integer id, @RequestBody Course course){
        course.setId(id);
        courseService.update(course);
        return Response.ok().build();
    }

    @DELETE
    @Path("/courses/{id}")
    public Response deleteCourse(@PathParam("id") Integer id){
        courseService.delete(id);
        return Response.ok().build();
    }

}
