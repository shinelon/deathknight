package com.shinelon.deathknight.spel;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author shinelon
 */
public class ParamDemo {

    private Long paramLong;
    private String paramString;
    private LocalDate paramLocalDate;
    private Date paramDate;
    private List<String> listString;
    private List<Bean> beanList;


    public static class Bean {
        private Long beanParamLong;
        private String beanParamString;

        public Long getBeanParamLong() {
            return beanParamLong;
        }

        public void setBeanParamLong(Long beanParamLong) {
            this.beanParamLong = beanParamLong;
        }

        public String getBeanParamString() {
            return beanParamString;
        }

        public void setBeanParamString(String beanParamString) {
            this.beanParamString = beanParamString;
        }
    }

    public Long getParamLong() {
        return paramLong;
    }

    public void setParamLong(Long paramLong) {
        this.paramLong = paramLong;
    }

    public String getParamString() {
        return paramString;
    }

    public void setParamString(String paramString) {
        this.paramString = paramString;
    }

    public LocalDate getParamLocalDate() {
        return paramLocalDate;
    }

    public void setParamLocalDate(LocalDate paramLocalDate) {
        this.paramLocalDate = paramLocalDate;
    }

    public Date getParamDate() {
        return paramDate;
    }

    public void setParamDate(Date paramDate) {
        this.paramDate = paramDate;
    }

    public List<String> getListString() {
        return listString;
    }

    public void setListString(List<String> listString) {
        this.listString = listString;
    }

    public List<Bean> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<Bean> beanList) {
        this.beanList = beanList;
    }
}
