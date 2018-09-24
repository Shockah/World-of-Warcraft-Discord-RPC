local addonName, addonTable = ...
local Addon = _G[addonName]
local S = LibStub:GetLibrary("ShockahUtils")

local Class = {}
Addon.IslandExpeditionActivitySerializer = Class

local function GetMappedDifficulty(difficultyID)
	if difficultyID == 38 then -- Normal -- TODO: confirm
		return 0
	elseif difficultyID == 39 then -- Heroic -- TODO: confirm
		return 1
	elseif difficultyID == 40 then -- Mythic
		return 2
	elseif difficultyID == 29 then -- PVP -- TODO: confirm
		return 3
	end
	error("Unknown island expedition difficulty.")
end

function Class:Serialize(bits)
	local name, instanceType, difficultyID, difficultyName, maxPlayers, dynamicDifficulty, isDynamic, instanceMapID, instanceGroupSize = GetInstanceInfo()

	if self:IncludeDifficulty() then
		bits:Write(true)
		bits:WriteUInt(2, GetMappedDifficulty(difficultyID))
	else
		bits:Write(false)
	end

	if self:IncludeProgress() then
		bits:Write(true)
		bits:WriteUInt(15, 0) -- TODO: actual player progress
		bits:WriteUInt(15, 0) -- TODO: actual enemy progress
		bits:WriteUInt(15, 12000) -- TODO: actual max progress
	else
		bits:Write(false)
	end

	-- TODO:
	bits:Write(false) -- activity time
end

function Class:IncludeDifficulty()
	return true
end

function Class:IncludeProgress()
	return true
end