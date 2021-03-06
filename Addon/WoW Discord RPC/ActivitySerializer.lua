local addonName, addonTable = ...
local Addon = _G[addonName]
local S = LibStub:GetLibrary("ShockahUtils")

local Class = {}
Addon.ActivitySerializer = Class

function Class:Serialize(bits)
	local name, instanceType, difficultyID, difficultyName, maxPlayers, dynamicDifficulty, isDynamic, instanceMapID, instanceGroupSize = GetInstanceInfo()

	if instanceType == "party" then
		Addon.DungeonActivitySerializer:Serialize(bits)
	elseif instanceType == "raid" then
		Addon.RaidActivitySerializer:Serialize(bits)
	elseif instanceType == "scenario" and (difficultyID == 38 or difficultyID == 39 or difficultyID == 40) then -- TODO: include PVP difficulty
		Addon.IslandExpeditionActivitySerializer:Serialize(bits)
	else
		error("Unknown activity.")
	end
end