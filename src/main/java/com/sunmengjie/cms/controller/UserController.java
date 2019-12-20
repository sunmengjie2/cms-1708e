package com.sunmengjie.cms.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.sunmengjie.cms.common.CmsContant;
import com.sunmengjie.cms.common.CmsError;
import com.sunmengjie.cms.common.CmsMessage;
import com.sunmengjie.cms.entity.Articles;
import com.sunmengjie.cms.entity.Category;
import com.sunmengjie.cms.entity.Channel;
import com.sunmengjie.cms.entity.User;
import com.sunmengjie.cms.service.ArticlesService;
import com.sunmengjie.cms.service.UserService;
import com.sunmengjie.cms.utils.FileUtils;
import com.sunmengjie.cms.utils.HtmlUtils;
import com.sunmengjie.cms.utils.StringUtils;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ArticlesService articlesService;
	
	
	@Value("${upload.path}")
	String picRootPath;
	
	@Value("${pic.path}")
	String picUrl;
	

	/**
	 * 进入个人中心
	 * @return
	 */
	@RequestMapping("home")
	public String home() {
		return "user/home";
	}
	
	
	
	/**
	 * 跳转到注册页面
	 */
	@RequestMapping(value="register",method=RequestMethod.GET)
	public String toRegister(Model m) {
		
		User user = new User();
		m.addAttribute("user", user);
		
		return "user/register";
	}
	
	
	/**
	 * 注册页面发过来的请求
	 * @param user
	 * @param result
	 * @param m
	 * @return
	 */
	@RequestMapping(value="register",method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user")User user,BindingResult result,Model m) {
		//有错误返回到注册页面
		if(result.hasErrors()) {
			return "user/register";
		}
		
		//进行唯一验证
		User solename =userService.getUserByUsername(user.getUsername());
		if(solename!=null) {
			result.rejectValue("username", "", "该用户名已存在");
			return "user/register";
		}
		
		//密码的校验
		if(StringUtils.isNumber(user.getPassword())) {
			result.rejectValue("password", "", "密码不能全是数字");
			
			return "user/register";
		}
		
		//去注册
		int register = userService.register(user);
		if(register<1) {
			m.addAttribute("error", "注册失败，请稍后再试");
			return "user/register";
		}
		
		return "redirect:login";
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping("checkname")
	@ResponseBody
	public boolean checkname(String username) {
		User solename =userService.getUserByUsername(username);
		
		return solename==null;
	}
	
	/**
	 * 跳转到登录页面
	 * @return
	 */
	@RequestMapping(value = "login",method =RequestMethod.GET)
	public String toLogin() {
		
		return "user/login";
	}
	
	
	/**
	 * 登录页面发送过来的请求
	 * @param user
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "login",method =RequestMethod.POST)
	public String login(User user,Model m,HttpSession session) {
		
		User loginuser = userService.login(user);
		
		//登录失败
		if(loginuser==null) {
			m.addAttribute("error", "用户名或密码错误");
			return "user/login";
		}
		
		//登录成功， 把用户信息存放到session里
		session.setAttribute(CmsContant.USER_KEY, loginuser);
		
		System.out.println(loginuser.getRole());
		//判断用户是否是管理员  进入管理页面
		if(loginuser.getRole()==CmsContant.USER_ROLE_ADMIN) {
			
			return "redirect:/admin/index";
		}
		
		
		//普通用户 进去个人中心
		return "redirect:home";
	}
	
	/**
	 * 登录页面发送过来的请求
	 * @param user
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "loginIndex",method =RequestMethod.POST)
	@ResponseBody
	public CmsMessage loginIndex(String  username,String password,Model m,HttpSession session) {
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		User loginuser = userService.login(user);
		
		//登录失败
		if(loginuser==null) {
			return new CmsMessage(CmsError.NOT_EXIST, "用户名或密码有误", null);
		}
		
		//登录成功， 把用户信息存放到session里
		session.setAttribute(CmsContant.USER_KEY, loginuser);
		
		
		
		return new  CmsMessage(CmsError.SUCCESS, "", "登录成功");
	}
	
	/**
	 * 我的文章
	 * @return
	 */
	@RequestMapping("articles")
	public String articles(Model m,@RequestParam(defaultValue = "1")int pageNum,HttpSession session) {
		
		//获取用户
		User loginUser = (User) session.getAttribute(CmsContant.USER_KEY);
	
		PageInfo<Articles> articlesPage =  articlesService.listByUser(loginUser.getId(),pageNum);
		
		m.addAttribute("articlesPage", articlesPage);
		
		return "user/articles/list";
	}
	
	
	
	/**
	 * 	发表文章
	 * @return
	 */
	@RequestMapping("postArticle")
	public String contribute(Model m) {
		
		//查询所有栏目
		List<Channel> channels= articlesService.getChannels();
		
		m.addAttribute("channels", channels);
	
		return "user/articles/post";
	}
	
	
	/**
	 * 获取所有分类
	 * @param cid
	 * @return
	 */
	@RequestMapping("getCategoris")
	@ResponseBody
	public List<Category> getCategoris(int cid) {
		List<Category>  categoris = articlesService.getCategorisByCid(cid);
		return categoris;							
	}
	
	
	/**
	 * 
	 * @param m
	 * @param articles
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "postArticles",method = RequestMethod.POST)
	@ResponseBody
	public boolean postArticle(Model m, Articles articles, MultipartFile file,HttpSession session) {
		
		String picUrl;
		
		// 处理上传文件
		
		try {
			picUrl = processFile(file);
			articles.setPicture(picUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//当前用户是文章的作者
		User loginUser = (User)session.getAttribute(CmsContant.USER_KEY);
		articles.setUserId(loginUser.getId());
		
		
		
		return articlesService.add(articles)>0;
	}
	
	
	private String processFile(MultipartFile file) throws IllegalStateException, IOException {
		// 判断目标目录时间否存在
		//picRootPath + ""
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String subPath = sdf.format(new Date());
		//图片存放的路径
		File path= new File(picRootPath+"/" + subPath);
		//路径不存在则创建
		if(!path.exists())
			path.mkdirs();
		
		String suffixName = FileUtils.getSuffixName(file.getOriginalFilename());
		
		//随机生成文件名
		String fileName = UUID.randomUUID().toString() + suffixName;
		//文件另存
		file.transferTo(new File(picRootPath+"/" + subPath + "/" + fileName));
		
		return  subPath + "/" + fileName;
	}

	
	/**
	 * 退出
	 * @param session
	 * @return
	 */
	@RequestMapping("exit")
	public String exit(HttpSession session) {
		session.removeAttribute(CmsContant.USER_KEY);
		
		return "user/login";
	}
	
	/**
	 * 我的文章删除
	 * @param username
	 * @return
	 */
	@RequestMapping("deletearticle")
	@ResponseBody
	public boolean deleteArticle(int id) {
		int result  = articlesService.delete(id);
		return result > 0;
	}
	
	/**
	 *	 跳转到修改文章的页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateArticles",method = RequestMethod.GET)
	public String updateArticles(Model m,int id,HttpSession session) {
		
		//获取栏目
		List<Channel> channels= articlesService.getChannels();
		m.addAttribute("channels", channels);
		
		//获取文章
		Articles articles =  articlesService.getById(id);
		User loginUser = (User) session.getAttribute(CmsContant.USER_KEY);
		
		if(loginUser.getId() != articles.getUserId()) {
			// todo 准备做异常处理的！！
		}
		m.addAttribute("articles", articles);
		m.addAttribute("content1",  HtmlUtils.htmlspecialchars(articles.getContent()));
		
		
		return "user/articles/update";
	}
	
	/**
	 * 	修改
	 * @param articles
	 * @param file
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "updateArticles",method = RequestMethod.POST)
	@ResponseBody
	public  boolean  updateArticle(Articles articles,MultipartFile file,HttpSession session) {
		
		System.out.println("articles is  "  + articles);
		
		String picUrl;
		if(file.getOriginalFilename()!=null && file.getOriginalFilename()!="") {
			try {
				// 处理上传文件
				picUrl = processFile(file);
				articles.setPicture(picUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//当前用户是文章的作者
		User loginUser = (User)session.getAttribute(CmsContant.USER_KEY);
		//article.setUserId(loginUser.getId());
		int updateREsult  = articlesService.update(articles,loginUser.getId());
		
		
		return updateREsult>0;
		
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("comments")
	public String comments() {
		return "user/comments/list";
	}
	
	/**
	 * 个人设置
	 * @return
	 */
	@RequestMapping("personal")
	public String personal() {
		return "user/personal/list";
	}
	
}
