/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esafeafrica.esafe.Model;


/**
 * @author Sadam
 */

public class Notify {
    private String to, topic, title, body, msg;

    /**
     *
     */
    public Notify() {
    }

    /**
     * @param to
     * @param topic
     * @param title
     * @param body
     * @param msg
     */
    public Notify(String to, String topic, String title, String body, String msg) {
        this.to = to;
        this.topic = topic;
        this.title = title;
        this.body = body;
        this.msg = msg;
    }

    /**
     * @return
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @param topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }


}
