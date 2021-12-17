package com.example.demoo;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import com.example.demoo.controller.StudentController;
import com.example.demoo.model.Course;
import com.example.demoo.service.StudentService;
import org.testng.annotations.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    Course mockCourse = new Course("Course1", "Spring", "10 Steps",
            Arrays.asList("Learn Maven", "Import Project", "First Example",
                    "Second Example")); // mockando um curso com os atributos

    String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";
    // string de um curso em json
    @Test
    public void retrieveDetailsForCourse() throws Exception {
        // testando os detalhes do curso
        Mockito.when(
                studentService.retrieveCourse(Mockito.anyString(),
                        Mockito.anyString())).thenReturn(mockCourse);
        // quando o estudante pega os curso, retorna o mock do curso

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/students/Student1/courses/Course1").accept(
                MediaType.APPLICATION_JSON);
        // mockando a url, montando-a com a uri e a formato em json

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{id:sem_nome,name:Spring,description:10 Steps}";

        // {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import Project","First Example","Second Example"]}

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void createStudentCourse() throws Exception {
        Course mockCourse = new Course("1", "Smallest Number", "1",
                Arrays.asList("1", "2", "3", "4"));
        //criando um curso para o estudante

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(
                studentService.addCourse(Mockito.anyString(),
                        Mockito.any(Course.class))).thenReturn(mockCourse);

        // Send course as body to /students/Student1/courses
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/students/Student1/courses")
                .accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
                .contentType(MediaType.APPLICATION_JSON);
        // mockando a url, montando-a com a uri e a formato em json

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        // verifica se

        MockHttpServletResponse response = result.getResponse();
        // verifica se a resposta está certa

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
         // verifica se o status http está certo
        assertEquals("http://localhost/students/Student1/courses/1",
                response.getHeader(HttpHeaders.LOCATION));
        //verifica se o header do método http está certo

    }

}

