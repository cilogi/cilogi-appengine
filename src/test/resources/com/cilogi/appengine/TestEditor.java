// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestEditor.java  (19/02/16)
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


package com.cilogi.appengine;

import com.cilogi.appengine.editor.Editor;
import com.cilogi.appengine.editor.Role;
import com.cilogi.util.Pickle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestEditor {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestEditor.class);


    public TestEditor() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testJSON() throws IOException {
        Editor editor = new Editor("tim@timniblett.net", "botanics");
        editor.setRole(Role.owner);
        editor.setHandle("TimN");
        String json  = editor.toJSONString();
        Editor back = new ObjectMapper().readValue(json, Editor.class);
        assertEquals(editor, back);
    }

    @Test
    public void testSerialize() throws IOException {
        Editor editor = new Editor("tim@timniblett.net", "botanics");
        editor.setRole(Role.owner);
        editor.setHandle("TimN");
        byte[] data = Pickle.pickle(editor);
        Editor back = (Editor)Pickle.unpickleClean(data);
        assertEquals(editor, back);
    }
}