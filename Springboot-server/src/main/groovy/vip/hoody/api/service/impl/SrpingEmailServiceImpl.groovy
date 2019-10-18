package vip.hoody.api.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import vip.hoody.api.service.IEmailService

import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

/**
 * 邮件服务类实现
 */
@Component
class SrpingEmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender
    /**
     * 邮件发送者
     */
    @Value('${spring.mail.from}')
    private String from

    @Override
    void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage()
        message.setFrom(from)
        message.setTo(to)
        message.setSubject(subject)
        message.setText(content)
        mailSender.send(message)
    }

    @Override
    void sendSimpleMail(String to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage()
        message.setFrom(from)
        message.setTo(to)
        message.setCc(cc)
        message.setSubject(subject)
        message.setText(content)
        mailSender.send(message)
    }

    @Override
    void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage()
        message.setFrom(from)
        message.setTo(to)
        message.setSubject(subject)
        message.setText(content)
        mailSender.send(message)
    }

    @Override
    void sendHtmlMail(String to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage()
        message.setFrom(from)
        message.setTo(to)
        message.setCc(cc)
        message.setSubject(subject)
        message.setText(content)
        mailSender.send(message)
    }

    @Override
    void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);

        mailSender.send(message);
    }

    @Override
    void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setCc(cc)
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);

        mailSender.send(message);
    }

    @Override
    void sendResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);

        mailSender.send(message);
    }

    @Override
    void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc) {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setCc(cc)
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);

        mailSender.send(message);
    }
}
