package programmers.nbe5_7_1_8bit.domain.manager.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import programmers.nbe5_7_1_8bit.domain.manager.service.AuthService;
import programmers.nbe5_7_1_8bit.domain.manager.utils.SessionUtils;

@WebMvcTest(ManagerController.class)
@Import({ManagerControlerTestConfig.class})
class ManagerControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockitoBean
  private AuthService authService;

  @Test
  void should_authenticate_when_haveSessionId() throws Exception {
    when(authService.login(any())).thenReturn(true);
    mockMvc.perform(post("/login").param("password", ""))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/dashboard"));
  }

  @Test
  void should_failAuthenticate_when_notHaveSessionId() throws Exception {
    mockMvc.perform(get("/dashboard"))
        .andExpect(status().isUnauthorized());
  }


  @Test
  void should_AuthenticateContain_when_HaveSessionId() throws Exception {
    // given
    MockHttpSession session = new MockHttpSession();

    // when
    when(authService.login(any())).thenReturn(true);

    // then
    MvcResult loginResult = mockMvc.perform(post("/login").param("password", "").session(session))
        .andExpect(status().is3xxRedirection())
        .andReturn();

    mockMvc.perform(
            get("/dashboard").session((MockHttpSession) loginResult.getRequest().getSession()))
        .andExpect(status().isOk());
  }

  @Test
  void should_logout_when_outOfSession(@Autowired SessionUtils sessionUtils) throws Exception {
    // given
    MockHttpSession session = new MockHttpSession();

    // when
    when(authService.login(any())).thenReturn(true);

    // then
    MvcResult loginResult = mockMvc.perform(post("/login").param("password", "").session(session))
        .andExpect(status().is3xxRedirection())
        .andReturn();
    HttpSession loginSession = loginResult.getRequest().getSession();
    assertThat(sessionUtils.getSessionAttribute(loginSession)).isNotNull();

    mockMvc.perform(
            get("/dashboard").session((MockHttpSession) loginSession))
        .andExpect(status().isOk());

    MvcResult logoutResult = mockMvc.perform(
            get("/logout").session((MockHttpSession) loginSession)
        ).andExpect(status().isOk())
        .andReturn();

    HttpSession logoutSession = logoutResult.getRequest().getSession();
    assertThat(sessionUtils.getSessionAttribute(logoutSession)).isNull();
  }
}
