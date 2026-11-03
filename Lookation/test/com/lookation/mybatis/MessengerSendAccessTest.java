package com.lookation.mybatis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;

import com.lookation.dao.IHostMessengerDAO;
import com.lookation.dao.IMemberMessengerDAO;
import com.lookation.dto.MessengerDTO;

@RunWith(MockitoJUnitRunner.class)
public class MessengerSendAccessTest
{
	@Mock
	private SqlSession sqlSession;

	@Mock
	private IHostMessengerDAO hostMessengerDAO;

	@Mock
	private IMemberMessengerDAO memberMessengerDAO;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpSession session;

	@InjectMocks
	private HostMessenger hostMessenger;

	@InjectMocks
	private MemberMessenger memberMessenger;

	@Before
	public void setUp()
	{
		when(request.getSession()).thenReturn(session);
		when(request.getContentType()).thenReturn(null);
		when(sqlSession.getMapper(IHostMessengerDAO.class)).thenReturn(hostMessengerDAO);
		when(sqlSession.getMapper(IMemberMessengerDAO.class)).thenReturn(memberMessengerDAO);
		when(request.getParameter("book_code")).thenReturn("B0001");
		when(request.getParameter("msg_code")).thenReturn("MSG01");
		when(request.getParameter("host_msg_content")).thenReturn("hello");
		when(request.getParameter("member_msg_content")).thenReturn("hello");
	}

	@Test
	public void hostCannotSendWithoutLogin() throws Exception
	{
		when(session.getAttribute("hostCode")).thenReturn(null);

		String view = hostMessenger.imgSend(request, new ExtendedModelMap(), new MessengerDTO());

		assertThat(view, is("redirect:loginform.action?identify=host"));
		verify(hostMessengerDAO, never()).hSendMsg(any(MessengerDTO.class));
	}

	@Test
	public void hostCannotSendToAnotherHostsReservation() throws Exception
	{
		when(session.getAttribute("hostCode")).thenReturn("H0001");
		when(hostMessengerDAO.checkInfo("B0001")).thenReturn("H9999");

		String view = hostMessenger.imgSend(request, new ExtendedModelMap(), new MessengerDTO());

		assertThat(view, is("../WEB-INF/views/common/wrongAccess.jsp?identify=host"));
		verify(hostMessengerDAO, never()).hSendMsg(any(MessengerDTO.class));
	}

	@Test
	public void hostCanSendToOwnReservation() throws Exception
	{
		when(session.getAttribute("hostCode")).thenReturn("H0001");
		when(hostMessengerDAO.checkInfo("B0001")).thenReturn("H0001");
		when(hostMessengerDAO.checkMessage("B0001", "MSG01")).thenReturn(1);

		String view = hostMessenger.imgSend(request, new ExtendedModelMap(), new MessengerDTO());

		assertThat(view, is("redirect:hmessenger.action?book_code=B0001"));
		verify(hostMessengerDAO).hSendMsg(any(MessengerDTO.class));
	}

	@Test
	public void memberCannotSendWithoutLogin() throws Exception
	{
		when(session.getAttribute("memberCode")).thenReturn(null);

		String view = memberMessenger.imgSend(request, new ExtendedModelMap(), new MessengerDTO());

		assertThat(view, is("redirect:loginform.action?identify=member"));
		verify(memberMessengerDAO, never()).mSendMsg(any(MessengerDTO.class));
	}

	@Test
	public void memberCannotUseAnotherMembersMessageThread() throws Exception
	{
		when(session.getAttribute("memberCode")).thenReturn("M0001");
		when(memberMessengerDAO.checkInfo("B0001")).thenReturn("M9999");

		String view = memberMessenger.imgSend(request, new ExtendedModelMap(), new MessengerDTO());

		assertThat(view, is("../WEB-INF/views/common/wrongAccess.jsp?identify=member"));
		verify(memberMessengerDAO, never()).mSendMsg(any(MessengerDTO.class));
	}
}
