/**
 * PermissionsEx
 * Copyright (C) zml and PermissionsEx contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ninja.leaping.permissionsex.sponge;

import com.google.common.base.Optional;
import ninja.leaping.permissionsex.util.command.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import java.util.List;

/**
 * Wrapper class between PEX commands and the Sponge command class
 */
public class PEXSpongeCommand implements CommandCallable {
    private final CommandSpec command;
    private final PermissionsExPlugin plugin;

    public PEXSpongeCommand(CommandSpec command, PermissionsExPlugin plugin) {
        this.command = command;
        this.plugin = plugin;
    }

    @Override
    public Optional<CommandResult> process(CommandSource commandSource, String arguments) throws CommandException {
        command.process(new SpongeCommander(plugin, commandSource), arguments);
        return Optional.of(CommandResult.empty());
    }

    @Override
    public boolean testPermission(CommandSource commandSource) {
        try {
            command.checkPermission(new SpongeCommander(plugin, commandSource));
        } catch (ninja.leaping.permissionsex.util.command.CommandException e) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource commandSource) {
        return Optional.of(command.getDescription(new SpongeCommander(plugin, commandSource)).build());
    }

    @Override
    public Optional<Text> getHelp(CommandSource commandSource) {
        return Optional.of(command.getExtendedDescription(new SpongeCommander(plugin, commandSource)).build());
    }

    @Override
    public Text getUsage(CommandSource commandSource) {
        return command.getUsage(new SpongeCommander(plugin, commandSource)).build();
    }

    @Override
    public List<String> getSuggestions(CommandSource commandSource, String commandLine) throws CommandException {
        return command.tabComplete(new SpongeCommander(plugin, commandSource), commandLine);
    }
}
