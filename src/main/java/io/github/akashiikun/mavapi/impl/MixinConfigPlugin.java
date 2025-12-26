package io.github.akashiikun.mavapi.impl;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class MixinConfigPlugin implements IMixinConfigPlugin {
	private static final Map<String, String> nameMap = new HashMap<>();
	@Override
	public void onLoad(String mixinPackage) {
		byte[] bytes = null;
		try {
			bytes = MixinConfigPlugin.class.getResourceAsStream("/io/github/akashiikun/mavapi/impl/asm/_Dummy.class").readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		ClassReader reader = new ClassReader(bytes);
		ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9) {
			@Override
			public MethodVisitor visitMethod(int access, String methodName, String methodDescriptor, String signature, String[] exceptions) {
				return new MethodVisitor(Opcodes.ASM9) {
					@Override
					public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
						if (false) {
							System.out.println(descriptor + ", " + visible);
						}
						return new AnnotationVisitor(Opcodes.ASM9) {
							@Override
							public void visit(String name, Object value) {
								if ("Lio/github/akashiikun/mavapi/impl/asm/_Dummy$Name;".equals(descriptor) && "value".equals(name) && value instanceof String string) {
									if ("Axolotl$Variant".equals(string) || "RandomSource".equals(string)) {
										nameMap.put(string, Type.getReturnType(methodDescriptor).getInternalName());
									} else {
										nameMap.put(string, methodName);
									}
								}
								super.visit(name, value);
							}
						};
					}
				};
			}
		};
		reader.accept(visitor, 0);

		if (false) {
			System.out.println(nameMap);
		}
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return null;
	}


	public static String Axolotl$Variant() {
		return nameMap.get("Axolotl$Variant");
	}

	public static String readAdditionalSaveData() {
		return nameMap.get("readAdditionalSaveData");
	}

	public static String getBreedOffspring() {
		return nameMap.get("getBreedOffspring");
	}
	public static String setPersistenceRequired() {
		return nameMap.get("setPersistenceRequired");
	}

	public static String RandomSource() {
		return nameMap.get("RandomSource");
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		if (mixinClassName.equals("io.github.akashiikun.mavapi.impl.mixin.AxolotlMixin")) {
			{
				MethodNode readAdditionalSaveData = targetClass.methods.stream().filter(a -> a.name.equals(readAdditionalSaveData())).findFirst().orElseThrow();
				InsnList oldList = readAdditionalSaveData.instructions;

				LdcInsnNode node = null;

				// Look for a string called "Variant"
				for (AbstractInsnNode instruction : readAdditionalSaveData.instructions) {
					if (instruction instanceof LdcInsnNode insnNode && "Variant".equals(insnNode.cst)) {
						node = insnNode;
						break;
					}
				}

				// Go backwards until we hit a label
				AbstractInsnNode from = IntStream.iterate(readAdditionalSaveData.instructions.indexOf(node), i -> i >= 0, i -> i - 1).filter(i -> readAdditionalSaveData.instructions.get(i) instanceof LabelNode).mapToObj(i -> (LabelNode) readAdditionalSaveData.instructions.get(i)).findFirst().orElse(null);//readAdditionalSaveData.instructions.get(readAdditionalSaveData.instructions.indexOf(node) - 1);
				AbstractInsnNode to = null;
				boolean getTheNextOne = false;

				// Find a CHECKCAST, then get the instruction after that (which is an Axolotl#setVariant(Axolotl.Variant) call)
				for (AbstractInsnNode instruction : readAdditionalSaveData.instructions) {
					if (getTheNextOne) {
						to = instruction;
						break;
					}

					if (instruction instanceof TypeInsnNode insnNode && insnNode.getOpcode() == Opcodes.CHECKCAST && insnNode.desc.equals(Axolotl$Variant())) {
						getTheNextOne = true;
					}
				}
				InsnList list = new InsnList();

				Textifier printer = new Textifier();
				TraceMethodVisitor traceMV = new TraceMethodVisitor(printer);
				boolean adding = true;

				// Re-add all instructions, excluding [from, to] (inclusive)
				for (AbstractInsnNode abstractInsnNode : oldList) {
					if (abstractInsnNode == from) adding = false;
					if (adding) {
						abstractInsnNode.accept(traceMV);
						list.add(abstractInsnNode);
					}
					if (abstractInsnNode == to) adding = true;
				}
				readAdditionalSaveData.instructions = list;
				if (false) {
					System.out.println("Printing: " + printer.text);
					printer.print(new PrintWriter(System.out));
				}
			}

			{
				MethodNode getBreedOffspring = targetClass.methods.stream().filter(a -> a.name.equals(getBreedOffspring())).findFirst().orElseThrow();
				InsnList oldList = getBreedOffspring.instructions;


				FieldInsnNode node = null;
				// find a FieldInsnNode that is of the type net.minecraft.util.RandomSource (the instruction we want to match is "GETFIELD net/minecraft/world/entity/animal/axolotl/Axolotl.random : Lnet/minecraft/util/RandomSource;")
				for (AbstractInsnNode instruction : getBreedOffspring.instructions) {
					if (instruction instanceof FieldInsnNode insnNode && insnNode.desc.contains(RandomSource())) {
						node = insnNode;
						break;
					}
				}
				// Go backwards until we hit a label
				AbstractInsnNode from = IntStream.iterate(getBreedOffspring.instructions.indexOf(node), i -> i > 0, i -> i - 1).filter(i -> getBreedOffspring.instructions.get(i) instanceof LabelNode).mapToObj(i -> (LabelNode) getBreedOffspring.instructions.get(i)).findFirst().orElse(null);//readAdditionalSaveData.instructions.get(readAdditionalSaveData.instructions.indexOf(node) - 1);
				AbstractInsnNode to;
				AbstractInsnNode upTo = null;
				// Find a call to Mob#setPersistenceRequired()
				for (AbstractInsnNode instruction : getBreedOffspring.instructions) {
					if (instruction instanceof MethodInsnNode insnNode && insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && ((MethodInsnNode) instruction).name.equals(setPersistenceRequired())) {
						if (false) {
							System.out.println(insnNode.name + " " + insnNode.owner + " " + insnNode.desc);
						}
						upTo = instruction;
						break;
					}
				}
				// Go backwards until we hit an INVOKEVIRTUAL (the instruction we want to match is Axolotl#setVariant(Axolotl.Variant))
				to = IntStream.iterate(getBreedOffspring.instructions.indexOf(upTo) - 1, i -> i >= 0, i -> i - 1).mapToObj(i -> getBreedOffspring.instructions.get(i)).filter(abstractInsnNode -> abstractInsnNode instanceof MethodInsnNode insnNode && insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL).findFirst().orElse(null);
				InsnList list = new InsnList();

				Textifier printer = new Textifier();
				TraceMethodVisitor traceMV = new TraceMethodVisitor(printer);
				boolean adding = true;
				// Re-add all instructions, excluding [from, to] (inclusive)
				for (AbstractInsnNode abstractInsnNode : oldList) {
					if (abstractInsnNode == from) adding = false;
					if (adding) {
						abstractInsnNode.accept(traceMV);
						list.add(abstractInsnNode);
					}
					if (abstractInsnNode == to) adding = true;
				}
				getBreedOffspring.instructions = list;
				if (false) {
					System.out.println("Printing: " + printer.text);
					printer.print(new PrintWriter(System.out));
				}
			}
		}
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}
}
