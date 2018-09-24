local addonName, addonTable = ...
local Addon = _G[addonName]
local S = LibStub:GetLibrary("ShockahUtils")

local Class = Addon:NewModule("ChallengeModeTracker")
Addon.ChallengeModeTracker = Class

local timeStarted = nil
local timeEnding = nil

function Class:OnEnable()
	self:RegisterEvent("CHALLENGE_MODE_START")
	self:RegisterEvent("CHALLENGE_MODE_RESET")
	self:RegisterEvent("CHALLENGE_MODE_COMPLETED")
end

function Class:GetCurrentStartedTime()
	return timeStarted
end

function Class:GetCurrentEndingTime()
	return timeEnding
end

function Class:CHALLENGE_MODE_START()
	local mapID = C_ChallengeMode.GetActiveChallengeMapID()
	if mapID then
		local timeLimit = select(3, C_ChallengeMode.GetMapUIInfo(mapID))
		UpdateTimes(0, timeLimit)
	end
end

function Class:CHALLENGE_MODE_RESET()
	ClearTimes()
end

function Class:CHALLENGE_MODE_COMPLETED()
	ClearTimes()
end

hooksecurefunc("Scenario_ChallengeMode_UpdateTime", function(...)
	Class:OnChallengeModeUpdateTime(...)
end)
hooksecurefunc("Scenario_ChallengeMode_ShowBlock", function(...)
	Class:OnChallengeModeShowBlock(...)
end)

local function ClearTimes()
	timeStarted = nil
	timeEnding = nil
end

local function UpdateTimes(elapsedTime, timeLimit)
	timeStarted = GetTime() - elapsedTime
	timeEnding = timeStarted + timeLimit
end

function Class:OnChallengeModeUpdateTime(block, elapsedTime)
	local mapID = C_ChallengeMode.GetActiveChallengeMapID()
	if mapID then
		local timeLimit = select(3, C_ChallengeMode.GetMapUIInfo(mapID))
		UpdateTimes(elapsedTime, timeLimit)
	end
end

function Class:OnChallengeModeShowBlock(timerID, elapsedTime, timeLimit)
	UpdateTimes(elapsedTime, timeLimit)
end