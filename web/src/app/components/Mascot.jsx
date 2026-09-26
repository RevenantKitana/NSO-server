'use client';

import { useState, useEffect, useRef, useCallback } from 'react';

const REACTION_BUBBLES = [
  'Chào bạn! 🥷',
  'Chơi vui nhé! 🎮',
  'Phi lợi nhuận ❤️',
  'Ninja School! ✨',
  'Cố lên bạn ơi! 🔥',
  'Hoài niệm xưa! 🍃',
  'Cày cuốc vui vẻ! 🍥',
  'Mod by Khánh 🚀',
  'Gặp NPC Okanehashi nhé! 🎁'
];

export default function Mascot({
  size = 48,
  className = '',
  interactive = true,
  showSpeechBubble = false,
}) {
  const containerRef = useRef(null);
  const [sheetType, setSheetType] = useState('directions'); // 'directions' | 'reactions'
  const [currentFrame, setCurrentFrame] = useState(4); // Frame 0..8 in 3x3 matrix (4 = center)
  const [bubble, setBubble] = useState('');
  const [isBouncing, setIsBouncing] = useState(false);
  const [particles, setParticles] = useState([]);
  
  const isAnimatingRef = useRef(false);
  const reactionTimerRef = useRef(null);
  const bubbleTimerRef = useRef(null);
  const sequenceTimerRef = useRef(null);

  // Convert frame number (0..8) to col and row (3x3 grid)
  const col = currentFrame % 3;
  const row = Math.floor(currentFrame / 3);

  // In 3x3 grid with background-size: 300% 300%:
  // col 0 = 0%, col 1 = 50%, col 2 = 100%
  // row 0 = 0%, row 1 = 50%, row 2 = 100%
  const bgPosX = col === 0 ? '0%' : col === 1 ? '50%' : '100%';
  const bgPosY = row === 0 ? '0%' : row === 1 ? '50%' : '100%';

  // Sequential cell animation runner: plays frames in order [f0, f1, ...]
  const playSequence = useCallback((frames, interval = 120, onComplete) => {
    if (sequenceTimerRef.current) clearInterval(sequenceTimerRef.current);
    isAnimatingRef.current = true;
    let step = 0;

    sequenceTimerRef.current = setInterval(() => {
      if (step < frames.length) {
        setCurrentFrame(frames[step]);
        step++;
      } else {
        clearInterval(sequenceTimerRef.current);
        sequenceTimerRef.current = null;
        isAnimatingRef.current = false;
        if (onComplete) onComplete();
      }
    }, interval);
  }, []);

  // Calculate direction frame based on cursor coordinates relative to Mascot center
  const handleMouseMove = useCallback(
    (e) => {
      if (!interactive || isAnimatingRef.current || sheetType === 'reactions' || !containerRef.current) {
        return;
      }

      const rect = containerRef.current.getBoundingClientRect();
      const centerX = rect.left + rect.width / 2;
      const centerY = rect.top + rect.height / 2;

      const dx = e.clientX - centerX;
      const dy = e.clientY - centerY;
      const dist = Math.hypot(dx, dy);

      // Deadzone around center: look straight/neutral (frame 4 = row 1, col 1)
      if (dist < 35) {
        setCurrentFrame(4);
        return;
      }

      // Convert angle to degrees (-180 to 180)
      const angle = Math.atan2(dy, dx) * (180 / Math.PI);

      // 8-directional mapping according to 3x3 grid:
      // Row 0: Top-Left (0), Top (1), Top-Right (2)
      // Row 1: Left (3), Center (4), Right (5)
      // Row 2: Bottom-Left (6), Bottom (7), Bottom-Right (8)
      let targetFrame = 4;

      if (angle >= -157.5 && angle < -112.5) {
        // Top-Left (row 0, col 0)
        targetFrame = 0;
      } else if (angle >= -112.5 && angle < -67.5) {
        // Top (row 0, col 1)
        targetFrame = 1;
      } else if (angle >= -67.5 && angle < -22.5) {
        // Top-Right (row 0, col 2)
        targetFrame = 2;
      } else if (angle >= -22.5 && angle < 22.5) {
        // Right (row 1, col 2)
        targetFrame = 5;
      } else if (angle >= 22.5 && angle < 67.5) {
        // Bottom-Right (row 2, col 2)
        targetFrame = 8;
      } else if (angle >= 67.5 && angle < 112.5) {
        // Bottom (row 2, col 1)
        targetFrame = 7;
      } else if (angle >= 112.5 && angle < 157.5) {
        // Bottom-Left (row 2, col 0)
        targetFrame = 6;
      } else {
        // Left (row 1, col 0)
        targetFrame = 3;
      }

      setCurrentFrame(targetFrame);
    },
    [interactive, sheetType]
  );

  useEffect(() => {
    if (!interactive) return;

    window.addEventListener('mousemove', handleMouseMove, { passive: true });
    return () => {
      window.removeEventListener('mousemove', handleMouseMove);
    };
  }, [interactive, handleMouseMove]);

  // Clean up timers on unmount
  useEffect(() => {
    return () => {
      if (reactionTimerRef.current) clearTimeout(reactionTimerRef.current);
      if (bubbleTimerRef.current) clearTimeout(bubbleTimerRef.current);
      if (sequenceTimerRef.current) clearInterval(sequenceTimerRef.current);
    };
  }, []);

  // Handle click on Mascot: trigger random reaction frame & particle pop
  const handleClick = (e) => {
    if (e) e.stopPropagation();
    if (!interactive) return;

    // Switch to reactions sheet
    setSheetType('reactions');
    
    // Pick a random frame index (0 to 8) from reactions 3x3 matrix
    const randomFrame = Math.floor(Math.random() * 9);
    
    // Pick random bubble text
    const randomText = REACTION_BUBBLES[Math.floor(Math.random() * REACTION_BUBBLES.length)];

    // Trigger bounce and particles
    setIsBouncing(true);
    setBubble(randomText);

    // Spawn 5 mini sparkles
    const newParticles = Array.from({ length: 5 }, (_, i) => ({
      id: Date.now() + i,
      x: (Math.random() - 0.5) * 60,
      y: -20 - Math.random() * 30,
      icon: ['✨', '⭐', '❤️', '🍥', '🔥'][i % 5],
    }));
    setParticles(newParticles);

    // Optional quick 2-frame roll or direct jump to random frame
    const rollSequence = [Math.floor(Math.random() * 9), randomFrame];
    playSequence(rollSequence, 80, () => {
      setCurrentFrame(randomFrame);
    });

    // Reset bounce scale
    setTimeout(() => setIsBouncing(false), 250);

    // Reset reaction timer
    if (reactionTimerRef.current) clearTimeout(reactionTimerRef.current);
    if (bubbleTimerRef.current) clearTimeout(bubbleTimerRef.current);

    bubbleTimerRef.current = setTimeout(() => {
      setBubble('');
      setParticles([]);
    }, 2200);

    // Return back to directions sheet & cursor tracking after 1500ms
    reactionTimerRef.current = setTimeout(() => {
      setSheetType('directions');
      setCurrentFrame(4); // Neutral center
      isAnimatingRef.current = false;
    }, 1500);
  };

  const currentImageUrl =
    sheetType === 'reactions'
      ? '/assets/icon/reactions.png'
      : '/assets/icon/directions.png';

  return (
    <div
      ref={containerRef}
      onClick={handleClick}
      style={{ width: size, height: size }}
      className={`relative inline-block select-none cursor-pointer group shrink-0 ${className}`}
      title="Nhấp vào để tương tác cùng Mascot!"
    >
      {/* Speech / Reaction Bubble */}
      {(bubble || showSpeechBubble) && (
        <div className="absolute -top-10 left-1/2 -translate-x-1/2 z-30 pointer-events-none whitespace-nowrap animate-bounce">
          <div className="px-2.5 py-1 rounded-lg bg-slate-900/95 border border-orange-500/40 text-orange-300 text-[11px] font-bold shadow-xl backdrop-blur-md flex items-center gap-1">
            {bubble || 'Xin chào!'}
          </div>
          <div className="w-2 h-2 bg-slate-900 border-r border-b border-orange-500/40 rotate-45 mx-auto -mt-1" />
        </div>
      )}

      {/* Floating particles on click */}
      {particles.map((p) => (
        <span
          key={p.id}
          style={{
            transform: `translate(${p.x}px, ${p.y}px)`,
            transition: 'all 0.8s cubic-bezier(0.2, 0.8, 0.2, 1)',
          }}
          className="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 text-xs pointer-events-none opacity-0 animate-ping"
        >
          {p.icon}
        </span>
      ))}

      {/* Mascot Container */}
      <div
        className={`w-full h-full rounded-2xl bg-gradient-to-br from-orange-500/40 via-amber-500/20 to-red-500/40 p-[2px] shadow-lg shadow-orange-500/20 group-hover:shadow-orange-500/50 group-hover:scale-105 transition-all duration-200 ${
          isBouncing ? 'scale-115 rotate-3' : ''
        }`}
      >
        <div className="w-full h-full bg-[#0d1117] rounded-[14px] overflow-hidden flex items-center justify-center relative backdrop-blur-sm border border-white/10">
          {/* Sprite Sheet Frame Display */}
          <div
            style={{
              width: '100%',
              height: '100%',
              backgroundImage: `url(${currentImageUrl})`,
              backgroundSize: '300% 300%',
              backgroundPosition: `${bgPosX} ${bgPosY}`,
              backgroundRepeat: 'no-repeat',
              imageRendering: 'auto',
            }}
            className="w-full h-full transition-[background-position] duration-75 ease-out"
          />
        </div>
      </div>
    </div>
  );
}
