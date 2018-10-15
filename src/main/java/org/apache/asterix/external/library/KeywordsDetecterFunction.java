/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.asterix.external.library;

import org.apache.asterix.external.api.IExternalScalarFunction;
import org.apache.asterix.external.api.IFunctionHelper;
import org.apache.asterix.external.library.java.base.JString;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class KeywordsDetecterFunction implements IExternalScalarFunction {

    private List<String> keywordsList;
    JString result;

    @Override
    public void evaluate(IFunctionHelper functionHelper) throws Exception {
        JString inputName = (JString) functionHelper.getArgument(0);
        result.setValue(String.valueOf(keywordsList.contains(inputName.getValue())));
        functionHelper.setResult(result);
    }

    @Override
    public void initialize(IFunctionHelper functionHelper) throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("KeywordsDetector.txt");
        keywordsList = IOUtils.readLines(in, StandardCharsets.UTF_8);

        result = (JString)functionHelper.getResultObject();
    }

    @Override
    public void deinitialize() {

    }
}
