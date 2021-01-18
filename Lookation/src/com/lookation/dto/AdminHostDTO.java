package com.lookation.dto;

public class AdminHostDTO
{
	// hostQnaManager 에서 사용할 주요 속성 구성
	private String loc_code,qna_content,qna_reply_content,host_nickname,qna_reply_date;
	private String qna_reply_code;
	private String member_nickname, qna_reply_remove_code, qna_reply_remove_date;
	private int count;
	private String review_reply_code,review_content,review_reply_content,review_reply_date,
	review_reply_remove_code,review_reply_remove_date;
	
	
	//getter / setter 구성
	public String getLoc_code()
	{
		return loc_code;
	}

	public void setLoc_code(String loc_code)
	{
		this.loc_code = loc_code;
	}

	public String getQna_content()
	{
		return qna_content;
	}

	public void setQna_content(String qna_content)
	{
		this.qna_content = qna_content;
	}

	public String getQna_reply_content()
	{
		return qna_reply_content;
	}

	public void setQna_reply_content(String qna_reply_content)
	{
		this.qna_reply_content = qna_reply_content;
	}

	public String getHost_nickname()
	{
		return host_nickname;
	}

	public void setHost_nickname(String host_nickname)
	{
		this.host_nickname = host_nickname;
	}

	public String getQna_reply_date()
	{
		return qna_reply_date;
	}

	public void setQna_reply_date(String qna_reply_date)
	{
		this.qna_reply_date = qna_reply_date;
	}
	
	public String getQna_reply_code()
	{
		return qna_reply_code;
	}

	public void setQna_reply_code(String qna_reply_code)
	{
		this.qna_reply_code = qna_reply_code;
	}
	public String getMember_nickname()
	{
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname)
	{
		this.member_nickname = member_nickname;
	}

	public String getQna_reply_remove_code()
	{
		return qna_reply_remove_code;
	}

	public void setQna_reply_remove_code(String qna_reply_remove_code)
	{
		this.qna_reply_remove_code = qna_reply_remove_code;
	}

	public String getQna_reply_remove_date()
	{
		return qna_reply_remove_date;
	}

	public void setQna_reply_remove_date(String qna_reply_remove_date)
	{
		this.qna_reply_remove_date = qna_reply_remove_date;
	}

	

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
	
	public String getReview_reply_code()
	{
		return review_reply_code;
	}

	public void setReview_reply_code(String review_reply_code)
	{
		this.review_reply_code = review_reply_code;
	}

	public String getReview_content()
	{
		return review_content;
	}

	public void setReview_content(String review_content)
	{
		this.review_content = review_content;
	}

	public String getReview_reply_content()
	{
		return review_reply_content;
	}

	public void setReview_reply_content(String review_reply_content)
	{
		this.review_reply_content = review_reply_content;
	}

	public String getReview_reply_date()
	{
		return review_reply_date;
	}

	public void setReview_reply_date(String review_reply_date)
	{
		this.review_reply_date = review_reply_date;
	}

	public String getReview_reply_remove_code()
	{
		return review_reply_remove_code;
	}

	public void setReview_reply_remove_code(String review_reply_remove_code)
	{
		this.review_reply_remove_code = review_reply_remove_code;
	}

	public String getReview_reply_remove_date()
	{
		return review_reply_remove_date;
	}

	public void setReview_reply_remove_date(String review_reply_remove_date)
	{
		this.review_reply_remove_date = review_reply_remove_date;
	}


}
