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

local function GetProgress()
	for widgetID, widgetFrame in UIWidgetManager:EnumerateWidgetsByWidgetTag("azeriteBar") do
		return widgetFrame.LeftBar:GetValue(), widgetFrame.RightBar:GetValue(), select(2, widgetFrame.LeftBar:GetMinMaxValues())
	end
	return nil
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

		local alliance, horde, target = GetProgress()
		local isAlliance = UnitFactionGroup("player") == "Alliance"
		local playerProgress = isAlliance and alliance or horde
		local enemyProgress = isAlliance and horde or alliance

		bits:WriteUInt(15, playerProgress)
		bits:WriteUInt(15, enemyProgress)
		bits:WriteUInt(15, target)
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