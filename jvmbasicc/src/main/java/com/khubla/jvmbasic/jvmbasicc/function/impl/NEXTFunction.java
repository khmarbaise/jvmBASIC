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
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

import com.khubla.jvmbasic.jvmbasicc.compiler.GenerationContext;
import com.khubla.jvmbasic.jvmbasicc.compiler.analysis.ForDeclaration;
import com.khubla.jvmbasic.jvmbasicc.function.BaseFunction;

/**
 * See the javadoc in FORFunction
 * 
 * @author tome
 */
public class NEXTFunction extends BaseFunction {
   @Override
   public boolean execute(GenerationContext generationContext) throws Exception {
      try {
         /*
          * check # parameters
          */
         if (generationContext.getCommonTree().getChildCount() == 1) {
            /*
             * get variable name
             */
            final String variableName = generationContext.getChildValue(0);
            /*
             * get the loop declaration
             */
            final ForDeclaration loopDeclaration = generationContext.getLoopDeclaration(variableName);
            /*
             * check
             */
            if (null != loopDeclaration) {
               /*
                * figure out the next line after the for; this is the loop target
                */
               final int nextLine = GenerationContext.getProgramStaticAnalysis().getNextLineNumber(loopDeclaration.getLineNumber());
               /*
                * find the label for that line
                */
               final Label loopTargetLabel = GenerationContext.getProgramStaticAnalysis().getLine(nextLine).getLabel();
               /*
                * compare the operands
                */
               generationContext.getMethodVisitor().visitVarInsn(ILOAD, loopDeclaration.getCounterLocalVariableIndex());
               generationContext.getMethodVisitor().visitVarInsn(ILOAD, loopDeclaration.getToLocalVariableIndex());
               /*
                * skip to end label if not
                */
               final Label endLoopLabel = new Label();
               generationContext.getMethodVisitor().visitJumpInsn(IF_ICMPGE, endLoopLabel);
               /*
                * i=i+step;
                */
               generationContext.getMethodVisitor().visitVarInsn(ILOAD, loopDeclaration.getCounterLocalVariableIndex());
               generationContext.getMethodVisitor().visitVarInsn(ILOAD, loopDeclaration.getStepLocalVariableIndex());
               generationContext.getMethodVisitor().visitInsn(IADD);
               generationContext.getMethodVisitor().visitVarInsn(ISTORE, loopDeclaration.getCounterLocalVariableIndex());
               /*
                * executionContext.setVariable('I', i);
                */
               generationContext.getMethodVisitor().visitVarInsn(ALOAD, 0);
               generationContext.getMethodVisitor().visitFieldInsn(GETFIELD, generationContext.getClassName(), "executionContext", "Lcom/khubla/jvmbasic/jvmbasicrt/ExecutionContext;");
               generationContext.getMethodVisitor().visitLdcInsn("I");
               generationContext.getMethodVisitor().visitVarInsn(ILOAD, loopDeclaration.getCounterLocalVariableIndex());
               generationContext.getMethodVisitor().visitMethodInsn(INVOKEVIRTUAL, "com/khubla/jvmbasic/jvmbasicrt/ExecutionContext", "setVariable", "(Ljava/lang/String;I)V");
               /*
                * jmp
                */
               generationContext.getMethodVisitor().visitJumpInsn(Opcodes.GOTO, loopTargetLabel);
               /*
                * the label at the bottom of the loop
                */
               generationContext.getMethodVisitor().visitLabel(endLoopLabel);
            } else {
               throw new Exception("No FOR loop was declared for variable '" + variableName + "'");
            }
         }
         return true;
      } catch (final Exception e) {
         throw new Exception("Exception in execute", e);
      }
   }
}
