package com.lookation.dto;

public class HelpDTO
{	
	//관리자 도움말 주요 속성 구성
	private String help_code
				, board_type
				, help_title
				, help_content
				, help_date
				, board_type_code
				, help_img_url
				, help_img_code;
	


	//getter, setter 구성
	public String getHelp_img_code()
	{
		return help_img_code;
	}

	public void setHelp_img_code(String help_img_code)
	{
		this.help_img_code = help_img_code;
	}

	public String getHelp_img_url()
	{
		return help_img_url;
	}

	public void setHelp_img_url(String help_img_url)
	{
		this.help_img_url = help_img_url;
	}
	public String getHelp_code()
	{
		return help_code;
	}

	public void setHelp_code(String help_code)
	{
		this.help_code = help_code;
	}

	public String getBoard_type()
	{
		return board_type;
	}

	public void setBoard_type(String board_type)
	{
		this.board_type = board_type;
	}

	public String getHelp_title()
	{
		return help_title;
	}

	public void setHelp_title(String help_title)
	{
		this.help_title = help_title;
	}

	public String getHelp_content()
	{
		return help_content;
	}

	public void setHelp_content(String help_content)
	{
		this.help_content = help_content;
	}

	public String getHelp_date()
	{
		return help_date;
	}

	public void setHelp_date(String help_date)
	{
		this.help_date = help_date;
	}

	public String getBoard_type_code()
	{
		return board_type_code;
	}

	public void setBoard_type_code(String board_type_code)
	{
		this.board_type_code = board_type_code;
	}
}
