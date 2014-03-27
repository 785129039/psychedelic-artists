package com.nex.web.spring.controller.ajax;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.annotation.Logger;
import com.nex.domain.Record;
import com.nex.domain.Statistic;

@Controller
@RequestMapping("/download/")
@Logger
public class DownloadController {

	@RequestMapping("{id}")
	public void download(@PathVariable("id") Long id, HttpServletResponse response) {
		Record rec = Record.findRecord(id);
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Length", String.valueOf(rec.getLength()));
	        response.setHeader("Content-Disposition","attachment;filename="+rec.getPath());
			rec.download(response.getOutputStream());
			saveStatistics(rec.getStatistic());
		} catch (Exception e) {
			log.error("", e);
			try {
				response.sendRedirect("/web/browse/overview/" + id);
			} catch (IOException e1) {
				log.error("", e);
			}
		}
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void saveStatistics(Statistic s) {
		s.addDownload();
		s.merge();
		s.flush();
	}
}
