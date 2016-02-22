// Copyright (c) 2015 Cilogi. All Rights Reserved.
//
// File:        Editor.java  (22/04/15)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Cilogi (the Author) and may not be used, sold, licenced, 
// transferred, copied or reproduced in whole or in part in 
// any manner or form or in or on any media to any person other than 
// in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//


package com.cilogi.appengine.editor;

import com.cilogi.util.Digest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.*;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Cache
@Entity
@EqualsAndHashCode()
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain=true)
public class Editor implements Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(Editor.class);
    private static final long serialVersionUID = -4258506735585454292L;

    @Id
    @Getter
    String id;

    @Getter
    @Setter
    private Role role;

    @Index
    @Getter
    private String email;

    @Getter
    @Setter
    private String handle;

    @Getter
    @Index
    private String guide;

    private Editor() {}

    public Editor(@NonNull String email, @NonNull String guide) {
        this.id = args2id(email, guide);
        this.email = email;
        this.guide = guide;
        role = Role.none;
    }

    public Editor autoId() {
        if (id == null && getEmail() != null && getGuide() != null) {
            id = args2id(getEmail(), getGuide());
        }
        return this;
    }

    public String toJSONString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOG.error("Can't convert Editor " + this + " to JSON string");
            return "{}";
        }
    }

    public static String args2id(@NonNull String email, @NonNull String guide) {
        return Digest.digestHex(email + guide, Digest.Algorithm.MD5);
    }
}
