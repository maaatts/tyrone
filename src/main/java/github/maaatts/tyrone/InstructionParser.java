package github.maaatts.tyrone;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.Printer;

import github.maaatts.tyrone.model.Instruction;

public class InstructionParser {
	public static Instruction toInstruction(int index, AbstractInsnNode ain, InsnList insns) {
		Instruction instruction = new Instruction();
		instruction.index = index;

		if (ain.getType() == AbstractInsnNode.LABEL) {
			instruction.opcode = "LABEL";
			instruction.params = " ";
			return instruction;
		}

		if (ain.getOpcode() == -1)
			return null;

		instruction.opcode = Printer.OPCODES[ain.getOpcode()];

		switch (ain.getType()) {
			default: {
				instruction.params = "unknown";
				break;
			}

			case AbstractInsnNode.INSN: {
				instruction.params = " ";
				break;
			}

			case AbstractInsnNode.INT_INSN: {
				IntInsnNode iin = (IntInsnNode) ain;
				instruction.params = Integer.toString(iin.operand);
				break;
			}

			case AbstractInsnNode.VAR_INSN: {
				VarInsnNode vin = (VarInsnNode) ain;
				instruction.params = "var" + Integer.toString(vin.var);
				break;
			}

			case AbstractInsnNode.TYPE_INSN: {
				TypeInsnNode tin = (TypeInsnNode) ain;
				instruction.params = tin.desc;
				break;
			}

			case AbstractInsnNode.FIELD_INSN: {
				FieldInsnNode fin = (FieldInsnNode) ain;

				StringBuilder sb = new StringBuilder();
				sb.append(fin.owner);
				sb.append('.');
				sb.append(fin.name);
				sb.append('(');
				sb.append(fin.desc);
				sb.append(')');

				instruction.params = sb.toString();
				break;
			}

			case AbstractInsnNode.METHOD_INSN: {
				MethodInsnNode min = (MethodInsnNode) ain;
				StringBuilder sb = new StringBuilder();
				sb.append(min.owner);
				sb.append('.');
				sb.append(min.name);
				sb.append('(');
				sb.append(min.desc);
				sb.append(')');

				instruction.params = sb.toString();
				break;
			}

			case AbstractInsnNode.JUMP_INSN: {
				JumpInsnNode jin = (JumpInsnNode) ain;
				instruction.params = Integer.toString(insns.indexOf(jin.label));
				break;
			}

			case AbstractInsnNode.LDC_INSN: {
				LdcInsnNode ldc = (LdcInsnNode) ain;
				instruction.params = ldc.cst.toString();
				break;
			}

			case AbstractInsnNode.IINC_INSN: {
				IincInsnNode iin = (IincInsnNode) ain;

				StringBuilder sb = new StringBuilder();
				sb.append("var");
				sb.append(iin.var);
				sb.append(" += ");
				sb.append(iin.incr);

				instruction.params = sb.toString();
				break;
			}
		}

		return instruction;
	}
}
