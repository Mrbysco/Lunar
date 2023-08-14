package com.mrbysco.lunar.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.LunarPhaseData;
import com.mrbysco.lunar.registry.ILunarEvent;
import com.mrbysco.lunar.registry.LunarRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class LunarCommands {
	public static void initializeCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
		final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(Constants.MOD_ID);
		root.requires((sourceStack) -> sourceStack.hasPermission(2))
				.then(Commands.literal("forceEvent")
						.then(Commands.argument("eventID", ResourceLocationArgument.id()).suggests((cs, builder) -> {
							return SharedSuggestionProvider.suggest(LunarRegistry.instance().getIDList(), builder);
						}).executes(LunarCommands::forceEvent)));
		dispatcher.register(root);
	}

	private static int forceEvent(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		final ResourceLocation eventID = ResourceLocationArgument.getId(ctx, "eventID");
		ServerLevel level = ctx.getSource().getServer().getLevel(Level.OVERWORLD);
		LunarPhaseData phaseData = LunarPhaseData.get(level);
		ILunarEvent event = LunarRegistry.instance().getEventByID(eventID);
		phaseData.setForcedEvent(event);

		return 0;
	}
}
