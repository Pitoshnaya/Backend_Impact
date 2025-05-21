//package Pitoshnaya.Impact.controller;
//
//import Pitoshnaya.Impact.entity.Pixel;
//import Pitoshnaya.Impact.service.CanvasService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//public class CanvasControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @Mock
//    private CanvasService canvasService;
//
//    @InjectMocks
//    private CanvasController canvasController;
//
//    @Test
//    public void testGetCanvas() throws Exception {
//        Pixel pixel1 = new Pixel(1, 1, "#ccc");
//        Pixel pixel2 = new Pixel(1, 2, "#aaa");
//
//        List<Pixel> mockCanvas = Arrays.asList(pixel1, pixel2);
//
//        when(canvasService.getCanvas()).thenReturn(mockCanvas); // 5. Условие для мока
//
//        mockMvc.perform(get("/grid/canvas"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$[0].x").value(1))
//            .andExpect(jsonPath("$[0].y").value(1))
//            .andExpect(jsonPath("$[0].color").value("#ccc"))
//            .andExpect(jsonPath("$[1].x").value(1))
//            .andExpect(jsonPath("$[1].y").value(2))
//            .andExpect(jsonPath("$[1].color").value("#aaa"));
//    }
//
//}
