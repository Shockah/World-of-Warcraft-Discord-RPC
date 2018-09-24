local addonName, addonTable = ...
local Addon = _G[addonName]
local S = LibStub:GetLibrary("ShockahUtils")

local Class = {}
Addon.DungeonActivitySerializer = Class

local function GetMappedDifficulty(difficultyID)
	if difficultyID == 1 then -- Normal
		return 0
	elseif difficultyID == 2 then -- Heroic
		return 1
	elseif difficultyID == 23 then -- Mythic
		return 2
	elseif difficultyID == 24 or difficultyID == 33 then -- Timewalking -- TODO: confirm
		return 3
	elseif difficultyID == 8 then -- Challenge Mode (Mythic+) -- TODO: confirm
		local level = C_ChallengeMode.GetActiveKeystoneInfo()
		if level then
			return level + 2
		end
	end
	error("Unknown dungeon difficulty.")
end

local function GetTimeStarted(difficultyID)
	if difficultyID == 8 then
		return Addon.ChallengeModeTracker:GetTimeStarted()
	end
	return nil
end

local function GetTimeEnding(difficultyID)
	if difficultyID == 8 then
		return Addon.ChallengeModeTracker:GetTimeEnding()
	end
	return nil
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

function Class:IncludeDungeon()
	return true
end

function Class:IncludeTimeStarted()
	return true
end

function Class:IncludeTimeEnding()
	return true
end