package Data;

import java.util.Date;

public class CFPInfo {
    private Integer id;
    private String title;
    private String abbreviation;
    private String url;
    private String location;
    private Date eventDateBegin;
    private Date eventDateEnd;
    private Date submissionDeadline;
    private String outline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEventDateBegin() {
        return eventDateBegin;
    }

    public void setEventDateBegin(Date eventDateBegin) {
        this.eventDateBegin = eventDateBegin;
    }

    public Date getEventDateEnd() {
        return eventDateEnd;
    }

    public void setEventDateEnd(Date eventDateEnd) {
        this.eventDateEnd = eventDateEnd;
    }

    public Date getSubmissionDeadline() {
        return submissionDeadline;
    }

    public void setSubmissionDeadline(Date submissionDeadline) {
        this.submissionDeadline = submissionDeadline;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }
}
