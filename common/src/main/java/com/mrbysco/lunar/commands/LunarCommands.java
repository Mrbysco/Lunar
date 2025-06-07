package com.mrbysco.lunar.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mrbysco.lunar.Constants;
import com.mrbysco.lunar.LunarPhaseData;
import com.mrbysco.lunar.api.ILunarEvent;
import com.mrbysco.lunar.registry.LunarRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class LunarCommands {
	public static void initializeCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
		final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(Constants.MOD_ID);
		root.requires((sourceStack) -> sourceStack.hasPermission(2))
				.then(Commands.literal("forceEvent")
						.then(Commands.argument("eventID", ResourceLocationArgument.id())
								.suggests((cs, builder) ->
										SharedSuggestionProvider.suggest(LunarRegistry.instance().getIDList(), builder))
								.executes((ctx) ->
										forceEvent(ctx, false)
								)
								.then(Commands.argument("forceCurrent", BoolArgumentType.bool())
										.executes((ctx) ->
												forceEvent(ctx, BoolArgumentType.getBool(ctx, "forceCurrent"))
										)
								)
						)
				);
		root.requires((sourceStack) -> sourceStack.hasPermission(2))
				.then(Commands.literal("skip")
						.executes(LunarCommands::skipEvent)
				);
		root.requires((sourceStack) -> sourceStack.hasPermission(2))
				.then(Commands.literal("randomize")
						.executes(LunarCommands::randomizeEvent)
				);
		dispatcher.register(root);
	}

	/**
	 * Forces a lunar event to occur, either immediately or for the next night.
	 * @param ctx the command context
	 * @param forceCurrent if true, forces the event to occur immediately; if false, sets it for the next night
	 * @return 0 if successful, or an error message if the event ID is invalid
	 */
	private static int forceEvent(CommandContext<CommandSourceStack> ctx, boolean forceCurrent) {
		final ResourceLocation eventID = ResourceLocationArgument.getId(ctx, "eventID");
		ServerLevel level = ctx.getSource().getServer().getLevel(Level.OVERWORLD);
		LunarPhaseData phaseData = LunarPhaseData.get(level);
		ILunarEvent event = LunarRegistry.instance().getEventByID(eventID);
		if (event == null) {
			ctx.getSource().sendFailure(Component.literal("No lunar event found with ID: " + eventID));
			return 0;
		}
		if (forceCurrent) {
			// Stop the current event effects
			phaseData.getActiveLunarEvent().stopEffects(level);
			// Set the current event to the forced event
			phaseData.setActiveEvent(event);
			// Sync the event to the clients
			phaseData.syncEvent(level);
			ctx.getSource().sendSuccess(() ->
					Component.literal("Successfully forced a " + Component.translatable(event.getTranslationKey()).getString() + " moon"), true);
		} else {
			// Set the forced moon for the next night
			phaseData.setForcedEvent(event);
			ctx.getSource().sendSuccess(() ->
					Component.literal("Successfully forced a " + Component.translatable(event.getTranslationKey()).getString() + " moon next night"), true);
		}
		return 0;
	}

	/**
	 * Skips the current lunar event for tonight, resetting it to the default moon.
	 * @param ctx the command context
	 * @return 0 if successful
	 */
	private static int skipEvent(CommandContext<CommandSourceStack> ctx) {
		ServerLevel level = ctx.getSource().getServer().getLevel(Level.OVERWORLD);
		LunarPhaseData phaseData = LunarPhaseData.get(level);
		// Stop the current event effects
		phaseData.getActiveLunarEvent().stopEffects(level);
		// Set the current event to the default moon
		phaseData.setDefaultMoon();
		// Sync the event to the clients
		phaseData.syncEvent(level);

		ctx.getSource().sendSuccess(() ->
				Component.literal("Successfully skipped the lunar event for tonight"), true);

		return 0;
	}

	/**
	 * Randomizes the lunar event for tonight, choosing a random event from the registry.
	 * @param ctx the command context
	 * @return 0 if successful
	 */
	private static int randomizeEvent(CommandContext<CommandSourceStack> ctx) {
		ServerLevel level = ctx.getSource().getServer().getLevel(Level.OVERWORLD);
		LunarPhaseData phaseData = LunarPhaseData.get(level);
		// Stop the current event effects
		phaseData.getActiveLunarEvent().stopEffects(level);
		// Choose a random lunar event
		phaseData.setRandomLunarEvent(level);
		// Sync the event to the clients
		phaseData.syncEvent(level);

		ctx.getSource().sendSuccess(() ->
				Component.literal("Successfully randomized the lunar event for tonight"), true);

		return 0;
	}
}
