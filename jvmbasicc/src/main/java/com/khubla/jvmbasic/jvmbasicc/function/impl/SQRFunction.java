package com.khubla.jvmbasic.jvmbasicc.function.impl;

/*
 * jvmBasic Copyright 2012, khubla.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import org.objectweb.asm.Opcodes;

import com.khubla.jvmbasic.jvmbasicc.compiler.GenerationContext;
import com.khubla.jvmbasic.jvmbasicc.compiler.RTLHelper;
import com.khubla.jvmbasic.jvmbasicc.function.BaseFunction;

/**
 * @author tome
 *         <p>
 *         executionContext.push(Math.SQR(this.executionContext.resolveValue(executionContext.pop())));
 *         </p>
 */
public class SQRFunction extends BaseFunction {
   @Override
   public boolean execute(GenerationContext generationContext) throws Exception {
      try {
         /*
          * get the operands
          */
         processTree(generationContext);
         /*
          * there should be 3 values, of which are ( <var> )
          */
         if (generationContext.getCommonTree().getChildCount() == 3) {
            generationContext.getMethodVisitor().visitVarInsn(Opcodes.ALOAD, 0);
            generationContext.getMethodVisitor().visitFieldInsn(Opcodes.GETFIELD, generationContext.getClassName(), RTLHelper.EXECUTIONCONTEXT_NAME, RTLHelper.JASIC_RUNTIME_EXECUTIONCONTEXT_TYPE);
            generationContext.getMethodVisitor().visitVarInsn(Opcodes.ALOAD, 0);
            generationContext.getMethodVisitor().visitFieldInsn(Opcodes.GETFIELD, generationContext.getClassName(), RTLHelper.EXECUTIONCONTEXT_NAME, RTLHelper.JASIC_RUNTIME_EXECUTIONCONTEXT_TYPE);
            generationContext.getMethodVisitor().visitVarInsn(Opcodes.ALOAD, 0);
            generationContext.getMethodVisitor().visitFieldInsn(Opcodes.GETFIELD, generationContext.getClassName(), RTLHelper.EXECUTIONCONTEXT_NAME, RTLHelper.JASIC_RUNTIME_EXECUTIONCONTEXT_TYPE);
            generationContext.getMethodVisitor().visitMethodInsn(Opcodes.INVOKEVIRTUAL, RTLHelper.JASIC_RUNTIME_EXECUTIONCONTEXT, "pop", "()Lcom/khubla/jvmbasic/jvmbasicrt/Value;");
            generationContext.getMethodVisitor().visitMethodInsn(Opcodes.INVOKEVIRTUAL, RTLHelper.JASIC_RUNTIME_EXECUTIONCONTEXT, "resolveValue",
                  "(Lcom/khubla/jvmbasic/jvmbasicrt/Value;)Lcom/khubla/jvmbasic/jvmbasicrt/Value;");
            generationContext.getMethodVisitor().visitMethodInsn(Opcodes.INVOKESTATIC, RTLHelper.JASIC_RUNTIME_MATH, "SQR", "(Lcom/khubla/jvmbasic/jvmbasicrt/Value;)Ljava/lang/Double;");
            generationContext.getMethodVisitor().visitMethodInsn(Opcodes.INVOKEVIRTUAL, RTLHelper.JASIC_RUNTIME_EXECUTIONCONTEXT, "push", "(Ljava/lang/Double;)V");
            return true;
         } else {
            throw new Exception("SQR requires a value");
         }
      } catch (final Exception e) {
         throw new Exception("Exception in execute", e);
      }
   }
}
