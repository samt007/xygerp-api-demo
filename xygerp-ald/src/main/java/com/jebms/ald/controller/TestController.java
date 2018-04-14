package com.jebms.ald.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Author: Sam.T
 * Email: samt007@qq.com
 * Date: 2017/8/28
 * Describe: 测试
 */
@Controller
public class TestController  {

	@ResponseBody
	@RequestMapping("/format")
	public Model postModel(){
		Model model=new Model(new Date());
		System.out.println("Model model"+model.toString());
		Date date=model.getDate();
		System.out.println("Date "+date);
		return model;
	}
	
	protected static class Model{
		
		private Date date;

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public Model(Date date) {
			super();
			this.date = date;
		}

		public Model() {
			super();
			
		}

		@Override
		public String toString() {
			return "Model [date=" + date + "]";
		}	
	}
}
