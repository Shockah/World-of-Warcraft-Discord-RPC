local addonName, addonTable = ...
local Addon = _G[addonName]
local S = LibStub:GetLibrary("ShockahUtils")

local Class = {}
Addon.Dungeon = Class

local dungeonZoneIdToKey = {}

local function SetupBattleForAzeroth()
	local toAdd = {
		[9028] = "ad", -- Atal'Dazar
		[9164] = "fh", -- Freehold
		[9526] = "kr", -- Kings' Rest
		[8064] = "ml", -- The MOTHERLODE!!
		[9525] = "ss", -- Shrine of the Storm
		[9354] = "sb", -- Siege of Boralus
		[9527] = "ts", -- Temple of Sethraliss
		[9327] = "td", -- Tol Dagor
		[9391] = "ur", -- Underrot
		[9424] = "wm", -- Waycrest Manor
	}

	for k, v in pairs(toAdd) do
		dungeonZoneIdToKey[k] = "8-"..v
	end
end

SetupBattleForAzeroth()

function Class:GetKey(zoneID)
	return dungeonZoneIdToKey[zoneID]
end