package com.exam.controller.poi.word;

import com.exam.common.Utils.DateUtil;
import com.exam.dao.ExamDao;
import com.exam.dao.ExamUserDao;
import com.exam.pojo.model.ExamModel;
import com.exam.pojo.model.ExamUserModel;
import com.exam.security.util.GetTokenInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URLEncoder;

/**
 * @author Gao Ge
 */
@Slf4j
@RestController
@RequestMapping("/word")
public class WordExportController {
    @Autowired
    private ExamUserDao examUserDao;
    @Autowired
    private ExamDao examDao;

    /**
     * word报告导出
     */
    @GetMapping(value = "/export-word")
    public void export(@RequestParam("examId") String examId, HttpServletResponse response) throws Exception {
        String username = GetTokenInfoUtil.getUsername();
        ExamUserModel examUserModel = examUserDao.queryByExamIdAndUserId(examId, username);
        ExamModel examModel = examDao.queryById(examUserModel.getExamId());
        //封装数据
        TicketInfoModel ticketInfoModel = new TicketInfoModel();
        ticketInfoModel.setPictureURL(PictureUrlToBase64.image2Base64(examUserModel.getIdentificationPhoto()));
        ticketInfoModel.setIDNum(examUserModel.getIdNumber());
        ticketInfoModel.setName(examUserModel.getApplyName());
        ticketInfoModel.setRoomNum(examUserModel.getExamRoom());
        ticketInfoModel.setTicketNum(examUserModel.getExamNumber());
        ticketInfoModel.setTitle(examModel.getTitle()+"准考证");
        ticketInfoModel.setStartTime(examModel.getStartTime());
        ticketInfoModel.setEndTime(examModel.getEndTime());

        XWPFDocument doc = new XWPFDocument(); //  创建Word文件

        // 标题
        XWPFParagraph p = doc.createParagraph();// 新建段落
        p.setAlignment(ParagraphAlignment.CENTER);// 设置段落的对齐方式
        XWPFRun r = p.createRun();//创建标题
        r.setText(ticketInfoModel.getTitle());
        r.setBold(true);//设置为粗体
        r.setColor("000000");//设置颜色
        r.setFontSize(21); //设置字体大小
        r.addCarriageReturn();//回车换行

        // 插入图片
        XWPFParagraph pImg = doc.createParagraph();
        pImg.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun rImg = pImg.createRun(); // 创建标题
//            rImg.addCarriageReturn();//回车换行
        String imgData = ticketInfoModel.getPictureURL().substring(ticketInfoModel.getPictureURL().indexOf(",") + 1);
        byte[] bytes = new BASE64Decoder().decodeBuffer(imgData);
        rImg.addPicture(new ByteArrayInputStream(bytes), Document.PICTURE_TYPE_PNG, "123.png", Units.toEMU(400), Units.toEMU(180));

        // 表格
        XWPFTable table = doc.createTable(3, 4);
        //列宽自动分割
        CTTblWidth infoTableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
        infoTableWidth.setType(STTblWidth.DXA);
        infoTableWidth.setW(BigInteger.valueOf(9072));

        setTableFonts(table.getRow(0).getCell(0), "姓名");
        setTableFonts(table.getRow(0).getCell(2), "准考证号");
        setTableFonts(table.getRow(0).getCell(1), ticketInfoModel.getName());
        setTableFonts(table.getRow(0).getCell(3), ticketInfoModel.getTicketNum());

        setTableFonts(table.getRow(1).getCell(0), "考场");
        setTableFonts(table.getRow(1).getCell(2), "身份证号");
        setTableFonts(table.getRow(1).getCell(1), ticketInfoModel.getRoomNum());
        setTableFonts(table.getRow(1).getCell(3), ticketInfoModel.getIDNum());

        setTableFonts(table.getRow(2).getCell(0), "开始时间");
        setTableFonts(table.getRow(2).getCell(2), "结束时间");
        setTableFonts(table.getRow(2).getCell(1), DateUtil.timeStamp2Date(String.valueOf(ticketInfoModel.getStartTime()),null));
        setTableFonts(table.getRow(2).getCell(3), DateUtil.timeStamp2Date(String.valueOf(ticketInfoModel.getEndTime()),null));

        // 段落
        createParagraph(doc, "考试须知: ");
        createParagraphTxt(doc, "1．考前三十分钟，考生需持符合报考规定的并与准考证显示信息一致的有效证件，进入规定的考场。");
        createParagraphTxt(doc, "2．考生入场后，按号入座，将本人《准考证》放在课桌上，以便核验。");
        createParagraphTxt(doc, "3．考场内不得相互借用文具。严禁在考场内饮食。");
        createParagraphTxt(doc, "4．考生离开考场时必须交卷，不准携带试卷离开考场。离开考场后不准在考场附近逗留和交谈。");
        createParagraphTxt(doc, "5．考生领到试卷后，必须先在指定位置准确，清楚地填写姓名，准考证号，座位号等栏目。");


        String fileName = examUserModel.getApplyName()+"-"+examModel.getTitle()+"准考证" +  ".doc";
        String fileNameURL = URLEncoder.encode(fileName, "UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileNameURL + ";" + "filename*=utf-8''" + fileNameURL);
        response.setContentType("application/octet-stream");
        //刷新缓冲
        response.flushBuffer();
        OutputStream ouputStream = response.getOutputStream();
        doc.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }


    /**
     * 创建段落
     *
     * @param text
     */
    private void createParagraph(XWPFDocument doc, String text) {
        XWPFParagraph paragraph = doc.createParagraph();  // 新建段落
        //paragraph.setAlignment(ParagraphAlignment.LEFT);// 设置段落的对齐方式
        paragraph.setFontAlignment(1);//字体对齐方式：1左对齐 2居中3右对齐
        XWPFRun run = paragraph.createRun();//创建标题
        run.setText(text);
        run.setColor("000000");//设置颜色
        run.setFontSize(14); //设置字体大小
        run.addCarriageReturn();//回车换行
    }

    /**
     * 创建考试说明格式
     *
     * @param text
     */
    private void createParagraphTxt(XWPFDocument doc, String text) {
        XWPFParagraph paragraph = doc.createParagraph();  // 新建段落
        //paragraph.setAlignment(ParagraphAlignment.LEFT);// 设置段落的对齐方式
        paragraph.setFontAlignment(1);//字体对齐方式：1左对齐 2居中3右对齐
        XWPFRun run = paragraph.createRun();//创建标题
        run.setText(text);
        run.setColor("000000");//设置颜色
        run.setFontSize(9); //设置字体大小
        run.addCarriageReturn();//回车换行
    }

    /**
     * 设置表格中字体
     *
     * @param cell
     * @param cellText
     */
    private static void setTableFonts(XWPFTableCell cell, String cellText) {
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph p = new XWPFParagraph(ctp, cell);
        p.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = p.createRun();
        run.setFontSize(14);
        run.setText(cellText);
        CTRPr rpr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("仿宋");
        fonts.setEastAsia("仿宋");
        fonts.setHAnsi("仿宋");
        cell.setParagraph(p);
    }

}

