/*
 * Copyright 2025 the original author or authors.
 * <p>
 * Licensed under the Moderne Source Available License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://docs.moderne.io/licensing/moderne-source-available-license
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.mainframe.internal;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.openrewrite.ExecutionContext;

/**
 * Adds an {@link ANTLRErrorListener} found in the execution context under {@link #KEY} to an ANTLR parser, so that a
 * test can watch prediction fall back to full context.
 */
public final class PredictionListener {

    public static final String KEY = "org.openrewrite.mainframe.predictionListener";

    private PredictionListener() {
    }

    public static void addTo(Parser parser, ExecutionContext ctx) {
        ANTLRErrorListener listener = ctx.getMessage(KEY);
        if (listener != null) {
            parser.addErrorListener(listener);
        }
    }
}
