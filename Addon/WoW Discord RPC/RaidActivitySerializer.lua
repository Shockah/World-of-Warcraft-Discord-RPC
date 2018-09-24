local addonName, addonTable = ...
local Addon = _G[addonName]
local S = LibStub:GetLibrary("ShockahUtils")

local Class = {}
Addon.RaidActivitySerializer = Class

local function GetMappedDifficulty(difficultyID)
	if difficultyID == 7 or difficultyID == 17 then -- Legacy LFR / LFR
		return 0
	elseif difficultyID == 3 or difficultyID == 4 or difficultyID == 9 or difficultyID == 14 then -- 10 Player / 25 Player / 40 Player / Normal
		return 1
	elseif difficultyID == 5 or difficultyID == 6 or difficultyID == 15 then -- 10 Player / 25 Player / Heroic
		return 2
	elseif difficultyID == 16 then -- Mythic
		return 3
	elseif difficultyID == 24 or difficultyID == 33 then -- Timewalking -- TODO: confirm
		return 4
	end
	error("Unknown raid difficulty.")
end

function Class:Serialize(bits)
	local name, instanceType, difficultyID, difficultyName, maxPlayers, dynamicDifficulty, isDynamic, instanceMapID, instanceGroupSize = GetInstanceInfo()

	if self:IncludeDifficulty() then
		bits:Write(true)
		bits:WriteUInt(3, GetMappedDifficulty(difficultyID))
	else
		bits:Write(false)
	end

	if self:IncludeRaid() then
		bits:Write(true)
		bits:WriteString(4, Addon.Dungeon:GetKey(C_Map.GetBestMapForUnit("player")))
	else
		bits:Write(false)
	end

	if self:IncludeComposition() then
		bits:Write(true)

		local dps = 0
		local healers = 0
		local tanks = 0

		for i in 1, MAX_RAID_MEMBERS do
			local name, _, _, _, _, _, _, _, _, _, _, combatRole = GetRaidRosterInfo(i)
			if name then
				if combatRole == "DAMAGER" then
					dps = dps + 1
				elseif combatRole == "HEALER" then
					healers = healers + 1
				elseif combatRole == "TANK" then
					tanks = tanks + 1
				end
			end
		end

		bits:WriteUInt(6, dps)
		bits:WriteUInt(6, healers)
		bits:WriteUInt(6, tanks)
	else
		bits:Write(false)
	end

	if self:IncludeEncounter() and UnitExists("boss1") then
		bits:Write(true)

		bits:Write(true) -- includes name
		bits:WriteString(7, UnitName("boss1"))

		bits:Write(true) -- includes health
		bits:WriteUInt(10, UnitHealth("boss1") / UnitHealthMax("boss1") * 1000)
	else
		bits:Write(false)
	end

	-- TODO:
	bits:Write(false) -- activity time
end

function Class:IncludeDifficulty()
	return true
end

function Class:IncludeRaid()
	return true
end

function Class:IncludeComposition()
	return true
end

function Class:IncludeEncounter()
	return true
end