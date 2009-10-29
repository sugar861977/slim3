/*
 * Copyright 2004-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.slim3.tester;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slim3.controller.ControllerConstants;
import org.slim3.controller.validator.Errors;
import org.slim3.tester.controller.HelloController;

/**
 * @author higa
 * 
 */
public class ControllerTesterTest {

    private ControllerTester tester = new ControllerTester(getClass());

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        tester.setUp();
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        tester.tearDown();
    }

    /**
     * @throws Exception
     * 
     */
    @Test
    public void start() throws Exception {
        tester.start("/");
        assertThat(tester.request.getServletPath(), is("/"));
    }

    /**
     * @throws Exception
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void startForBadPath() throws Exception {
        tester.start("xxx");
    }

    /**
     * @throws Exception
     * 
     */
    @Test
    public void startForForward() throws Exception {
        tester.start("/");
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/index.jsp"));
    }

    /**
     * @throws Exception
     * 
     */
    @Test
    public void startForRedirect() throws Exception {
        tester.start("/redirect");
        assertThat(tester.isRedirect(), is(true));
        assertThat(tester.getDestinationPath(), is("http://www.google.com"));
    }

    /**
     * @throws Exception
     * 
     */
    @Test
    public void startForJSP() throws Exception {
        tester.start("/index.jsp");
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/index.jsp"));
    }

    /**
     * @throws Exception
     * 
     */
    @Test(expected = IllegalStateException.class)
    public void getDestinationPathBeforStarting() throws Exception {
        tester.getDestinationPath();
    }

    /**
     * @throws Exception
     * 
     */
    @Test
    public void getController() throws Exception {
        tester.start("/hello");
        HelloController controller = tester.getController();
        assertThat(controller, is(not(nullValue())));
    }

    /**
     * @throws Exception
     * 
     */
    @Test(expected = IllegalStateException.class)
    public void getControllerBeforStarting() throws Exception {
        tester.getController();
    }

    /**
     * @throws Exception
     * 
     */
    @Test
    public void getErrors() throws Exception {
        tester.request.setAttribute(
            ControllerConstants.ERRORS_KEY,
            new Errors());
        assertThat(tester.getErrors(), is(not(nullValue())));
    }
}