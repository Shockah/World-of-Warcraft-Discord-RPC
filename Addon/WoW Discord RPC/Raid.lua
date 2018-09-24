local addonName, addonTable = ...
local Addon = _G[addonName]
local S = LibStub:GetLibrary("ShockahUtils")

local Class = {}
Addon.Dungeon = Class

local dungeonZoneIdToKey = {}

local function SetupBattleForAzeroth()
	local toAdd = {
		[9389] = "ul", -- Uldir
	}

	for k, v in pairs(toAdd) do
		dungeonZoneIdToKey[k] = "8-"..v
	end
end

SetupBattleForAzeroth()

function Class:GetKey(zoneID)
	return dungeonZoneIdToKey[zoneID]
end