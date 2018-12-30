package com.example.controller;

import com.example.domain.*;
import com.example.service.impl.*;
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
    private final LectureService lectureService;
    private final TestService testService;
    private final QuestionService questionService;

    public LearningController(ChapterService chapterService, CourseService courseService, LectureService lectureService, TestService testService, QuestionService questionService) {
        this.chapterService = chapterService;
        this.courseService = courseService;
        this.lectureService = lectureService;
        this.testService = testService;
        this.questionService = questionService;
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

    ////////////////////////////////////////////////////////

    @GET
    @Path("/courses/{courseId}/lectures/")
    public Response findLecturesOfChapter(@PathParam("courseId") Integer id) {
        return Response.ok(lectureService.getLecturesByCourseId(id)).build();
    }

    @GET
    @Path("/lectures/")
    public Response findLectures() {
        return Response.ok(lectureService.getAllLectures()).build();
    }

    @GET
    @Path("/lectures/{id}")
    public Response findLecture(@PathParam("id") Integer lectureId) {
        Lecture lecture = lectureService.getLecture(lectureId);
        if (lecture == null) {
            throw new NotFoundException("Lecture not found");
        }
        return Response.ok(lecture).build();
    }

    @GET
    @Path("/courses/{courseId}/lectures/{lectureId}")
    public Response findLecture(@PathParam("courseId") Integer chapterId, @PathParam("lectureId") Integer lectureId) {
        Lecture lecture = lectureService.getLecture(lectureId);
        if (lecture == null) {
            throw new NotFoundException("Lecture not found");
        }
        return Response.ok(lecture).build();
    }

    @POST
    @Path("/lectures")
    public Response createLecture(@RequestBody Lecture lecture) {
        lectureService.create(lecture);
        return Response.ok().build();
    }

    @PUT
    @Path("/lectures/{id}")
    public Response updateLecture(@PathParam("id") Integer id, @RequestBody Lecture lecture) {
        lecture.setId(id);
        lectureService.update(lecture);
        return Response.ok().build();
    }

    @DELETE
    @Path("/lectures/{id}")
    public Response deleteLecture(@PathParam("id") Integer id) {
        lectureService.delete(id);
        return Response.ok().build();
    }

    //////////////////////////////////////////////////////////

    @GET
    @Path("/courses/{courseId}/tests/")
    public Response findTestsOfChapter(@PathParam("courseId") Integer id) {
        return Response.ok(testService.getTestsByCourseId(id)).build();
    }

    @GET
    @Path("/tests/")
    public Response findTests() {
        return Response.ok(testService.getAllTests()).build();
    }

    @GET
    @Path("/tests/{id}")
    public Response findTest(@PathParam("id") Integer testId) {
        Test test = testService.getTest(testId);
        if (test == null) {
            throw new NotFoundException("Test not found");
        }
        return Response.ok(test).build();
    }

    @GET
    @Path("/courses/{courseId}/tests/{testId}")
    public Response findTest(@PathParam("courseId") Integer chapterId, @PathParam("testId") Integer testId) {
        Test test = testService.getTest(testId);
        if (test == null) {
            throw new NotFoundException("Test not found");
        }
        return Response.ok(test).build();
    }

    @POST
    @Path("/tests")
    public Response createTest(@RequestBody Test test) {
        testService.create(test);
        return Response.ok().build();
    }

    @PUT
    @Path("/tests/{id}")
    public Response updateTest(@PathParam("id") Integer id, @RequestBody Test test) {
        test.setId(id);
        testService.update(test);
        return Response.ok().build();
    }

    @DELETE
    @Path("/tests/{id}")
    public Response deleteTest(@PathParam("id") Integer id) {
        testService.delete(id);
        return Response.ok().build();
    }

    ////////////////////////////////////////////////////

    @GET
    @Path("/tests/{testId}/questions/")
    public Response findQuestionsOfChapter(@PathParam("testId") Integer id) {
        return Response.ok(questionService.getQuestionsByTestId(id)).build();
    }

    @GET
    @Path("/questions/")
    public Response findQuestions() {
        return Response.ok(questionService.getAllQuestions()).build();
    }

    @GET
    @Path("/questions/{id}")
    public Response findQuestion(@PathParam("id") Integer questionId) {
        Question question = questionService.getQuestion(questionId);
        if (question == null) {
            throw new NotFoundException("Question not found");
        }
        return Response.ok(question).build();
    }

    @GET
    @Path("/tests/{testId}/questions/{questionId}")
    public Response findQuestion(@PathParam("testId") Integer testId, @PathParam("questionId") Integer questionId) {
        Question question = questionService.getQuestion(questionId);
        if (question == null) {
            throw new NotFoundException("Question not found");
        }
        return Response.ok(question).build();
    }

    @POST
    @Path("/questions")
    public Response createQuestion(@RequestBody Question question) {
        questionService.create(question);
        return Response.ok().build();
    }

    @PUT
    @Path("/questions/{id}")
    public Response updateQuestion(@PathParam("id") Integer id, @RequestBody Question question) {
        question.setId(id);
        questionService.update(question);
        return Response.ok().build();
    }

    @DELETE
    @Path("/questions/{id}")
    public Response deleteQuestion(@PathParam("id") Integer id) {
        questionService.delete(id);
        return Response.ok().build();
    }
}
